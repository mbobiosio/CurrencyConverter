package com.mbobiosio.currencyconverter.model

import com.squareup.moshi.JsonClass

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
@JsonClass(generateAdapter = true)
data class Currencies(
    val currencies: String
)
