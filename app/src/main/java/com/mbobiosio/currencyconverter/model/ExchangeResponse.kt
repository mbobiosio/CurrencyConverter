package com.mbobiosio.currencyconverter.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
@JsonClass(generateAdapter = true)
data class ExchangeResponse(
    @Json(name = "base_currency_code")
    val base_currency_code: String,

    @Json(name = "base_currency_name")
    val base_currency_name: String,

    @Json(name = "amount")
    val amount: String,

    @Json(name = "updated_date")
    val updated_date: String,

    @Json(name = "rates")
    var rates: Map<String, Rates>,

    @Json(name = "status")
    var status: String,

    @Json(name = "error")
    val error: Error?
)
