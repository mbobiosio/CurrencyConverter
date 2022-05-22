package com.mbobiosio.currencyconverter.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mbobiosio.currencyconverter.data.local.entity.CurrencyResponse

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
@Database(entities = [CurrencyResponse::class], exportSchema = false, version = 2)
@TypeConverters(TypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val currencyDao: CurrencyDao
}
