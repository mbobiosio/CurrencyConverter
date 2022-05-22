package com.mbobiosio.currencyconverter.data.local

import androidx.room.TypeConverter
import com.mbobiosio.currencyconverter.domain.model.Rates
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

    @TypeConverter
    fun fromRates(rates: Rates): String =
        Moshi.Builder().build().adapter(Rates::class.java).toJson(rates)
/*

    @TypeConverter
    fun fromMap(value: String): Map<String, String> {
        val mapType = genericToken<Map<String, String>>()
        return Gson().fromJson(value, mapType)
    }

    @TypeConverter
    fun fromStringMap(map: Map<String, String>): String {
        return Gson().toJson(map)
    }
*/
}
