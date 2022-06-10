package com.mbobiosio.currencyconverter.domain

import com.mbobiosio.currencyconverter.domain.model.Error
import com.mbobiosio.currencyconverter.domain.model.ErrorResponse
import com.squareup.moshi.Moshi
import okio.IOException
import retrofit2.HttpException
import timber.log.Timber
import java.net.SocketTimeoutException
import java.net.UnknownHostException

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
                ResourceState.NetworkError("The connection request timed out. Please check your internet signal strength")
            }
            is UnknownHostException -> {
                ResourceState.NetworkError("No active internet connection")
            }
            is IOException -> ResourceState.NetworkError(
                "Connection detected without Internet access"
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
