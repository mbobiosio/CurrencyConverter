package com.mbobiosio.currencyconverter.data.local

import androidx.room.TypeConverter
import com.mbobiosio.currencyconverter.util.jsonToMap
import com.mbobiosio.currencyconverter.util.mapToJson
import com.squareup.moshi.Moshi

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
class TypeConverter {

    private val moshi = Moshi.Builder().build()

    @TypeConverter
    fun fromMapType(currency: String): Map<String, String>? =
        moshi.jsonToMap(currency)

    @TypeConverter
    fun fromString(map: Map<String, String>): String =
        moshi.mapToJson(map)
}
