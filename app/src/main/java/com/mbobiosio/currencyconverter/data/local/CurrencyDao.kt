package com.mbobiosio.currencyconverter.data.local

import androidx.room.* // ktlint-disable no-wildcard-imports
import com.mbobiosio.currencyconverter.data.local.entity.CurrencyResponse

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
@Dao
interface CurrencyDao {

    @Query("SELECT * FROM currencies")
    fun getAllCurrencies(): List<CurrencyResponse>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrency(currencyItem: CurrencyResponse)

    @Query("DELETE FROM currencies")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteCurrency(currencyResponse: CurrencyResponse)
}
