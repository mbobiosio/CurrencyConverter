package com.mbobiosio.currencyconverter.repository

import com.mbobiosio.currencyconverter.model.ConversionResponse
import com.mbobiosio.currencyconverter.model.CurrencyResponse
import com.mbobiosio.currencyconverter.network.ResourceState
import kotlinx.coroutines.flow.Flow

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
interface Repository {

    fun listCurrencies(): Flow<ResourceState<CurrencyResponse>>

    fun convertCurrencies(
        from: String?,
        to: String?,
        amount: Double
    ): Flow<ResourceState<ConversionResponse>>
}
