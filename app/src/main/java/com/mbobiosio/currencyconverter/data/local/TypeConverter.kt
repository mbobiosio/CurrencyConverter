package com.mbobiosio.currencyconverter.data.local

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
class TypeConverter {

    @TypeConverter
    fun fromMapType(currency: String): Map<String, String>? {
        val type = Types.newParameterizedType(
            MutableMap::class.java,
            String::class.java,
            String::class.java
        )
        return Moshi.Builder().build().adapter<Map<String, String>>(type).fromJson(currency)
    }

    @TypeConverter
    fun fromString(map: Map<String, String>): String {
        val type = Types.newParameterizedType(
            MutableMap::class.java,
            String::class.java,
            String::class.java
        )
        return Moshi.Builder().build().adapter<Map<String, String>>(type).toJson(map)
    }
}
