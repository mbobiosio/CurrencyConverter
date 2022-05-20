package com.mbobiosio.currencyconverter.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
@JsonClass(generateAdapter = true)
data class Error(
    @Json(name = "code")
    val code: Int,

    @Json(name = "message")
    val message: String
)
