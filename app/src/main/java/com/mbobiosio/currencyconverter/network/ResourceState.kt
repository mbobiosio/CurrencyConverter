package com.mbobiosio.currencyconverter.network

import com.mbobiosio.currencyconverter.model.ErrorResponse

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
sealed class ResourceState<out T> {
    // Successful
    data class Success<out T>(val data: T) : ResourceState<T>()

    // Error
    data class Error(val code: Int? = null, val response: ErrorResponse? = null) :
        ResourceState<Nothing>()

    // Unexpected
    data class NetworkError(val error: String? = null) : ResourceState<Nothing>()

    // Network Error
    object Loading : ResourceState<Nothing>()
}
