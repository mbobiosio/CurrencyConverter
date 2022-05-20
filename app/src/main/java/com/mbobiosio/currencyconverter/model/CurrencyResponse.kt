package com.mbobiosio.currencyconverter.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
@JsonClass(generateAdapter = true)
data class CurrencyResponse(
    @Json(name = "status")
    val status: String,

    @Json(name = "currencies")
    val currencies: Map<String, String>
)
