package com.mbobiosio.currencyconverter.data.remote.repository

import com.mbobiosio.currencyconverter.data.local.entity.CurrencyResponse
import com.mbobiosio.currencyconverter.domain.ResourceState
import com.mbobiosio.currencyconverter.domain.model.ConversionResponse
import kotlinx.coroutines.flow.Flow

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
interface Repository {

    suspend fun insertCurrency(currency: CurrencyResponse)

    suspend fun deleteItem(currency: CurrencyResponse)

    suspend fun deleteAll()

    suspend fun getAllCurrencies(): ResourceState<List<CurrencyResponse>>

    suspend fun listCurrencies(): Flow<ResourceState<List<CurrencyResponse>>>

    fun exchangeRates(
        from: String?,
        amount: Double
    ): Flow<ResourceState<ConversionResponse>>

    fun convertCurrencies(
        from: String?,
        to: String?,
        amount: Double
    ): Flow<ResourceState<ConversionResponse>>
}
