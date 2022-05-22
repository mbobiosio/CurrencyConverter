package com.mbobiosio.currencyconverter.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
@JsonClass(generateAdapter = true)
@Entity(tableName = "currencies")
data class CurrencyResponse(
    @PrimaryKey
    val id: Int? = 0,
    @Json(name = "status")
    val status: String,
    @Json(name = "currencies")
    val currencies: Map<String, String>
)
