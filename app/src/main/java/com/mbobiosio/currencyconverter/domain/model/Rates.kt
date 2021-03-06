package com.mbobiosio.currencyconverter.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
@JsonClass(generateAdapter = true)
data class Rates(
    @Json(name = "currency_name")
    val currencyName: String,
    @Json(name = "rate")
    val rate: Double,
    @Json(name = "rate_for_amount")
    val rateForAmount: Double
)
