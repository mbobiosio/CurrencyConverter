package com.mbobiosio.currencyconverter.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
@JsonClass(generateAdapter = true)
data class ConversionResponse(

    @Json(name = "base_currency_code")
    val baseCurrencyCode: String,

    @Json(name = "base_currency_name")
    val baseCurrencyName: String,

    @Json(name = "amount")
    val amount: String,

    @Json(name = "updated_date")
    val updatedDate: String,

    @Json(name = "rates")
    var rates: Map<String, Rates>,

    @Json(name = "status")
    var status: String,

    @Json(name = "error")
    val error: Error? = null
)
