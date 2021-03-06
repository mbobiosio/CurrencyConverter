package com.mbobiosio.currencyconverter.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
@JsonClass(generateAdapter = true)
data class ErrorResponse(

    @Json(name = "status")
    val status: String,

    @Json(name = "error")
    val error: Error
)
