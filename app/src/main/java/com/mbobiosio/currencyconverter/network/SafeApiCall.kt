package com.mbobiosio.currencyconverter.network

import com.mbobiosio.currencyconverter.model.Error
import com.mbobiosio.currencyconverter.model.ErrorResponse
import com.squareup.moshi.Moshi
import okio.IOException
import retrofit2.HttpException
import timber.log.Timber
import java.net.SocketTimeoutException

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
suspend fun <T> safeApiCall(
    apiCall: suspend () -> T
): ResourceState<T> {
    return try {
        ResourceState.Success(apiCall())
    } catch (t: Throwable) {
        when (t) {
            is SocketTimeoutException -> {
                ResourceState.NetworkError("Timed out")
            }

            is IOException -> ResourceState.NetworkError(
                t.message
            )
            is HttpException -> {
                val code = t.code()
                val message = errorResponse(t)

                ResourceState.Error(
                    code,
                    ErrorResponse("${message?.status}", Error(code, "${message?.error?.message}"))
                )
            }
            else -> ResourceState.Error(null, null)
        }
    }
}

private fun errorResponse(e: HttpException): ErrorResponse? =
    try {
        e.response()?.errorBody()?.source()?.let {
            val moshiAdapter = Moshi.Builder()
                .build()
                .adapter(ErrorResponse::class.java)
            moshiAdapter.fromJson(it)
        }
    } catch (t: Exception) {
        Timber.d("Error $t")
        null
    }
