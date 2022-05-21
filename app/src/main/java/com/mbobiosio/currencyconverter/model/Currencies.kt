package com.mbobiosio.currencyconverter.model

import com.squareup.moshi.JsonClass

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
@JsonClass(generateAdapter = true)
data class Currencies(
    val currency: String,
    val currencyName: String,
    val rate: Double,
    val rateForAmount: Double
)
