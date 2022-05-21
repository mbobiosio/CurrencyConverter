package com.mbobiosio.currencyconverter.repository

import com.mbobiosio.currencyconverter.model.ConversionResponse
import com.mbobiosio.currencyconverter.model.CurrencyResponse
import com.mbobiosio.currencyconverter.model.ExchangeResponse
import com.mbobiosio.currencyconverter.network.ApiService
import com.mbobiosio.currencyconverter.network.ResourceState
import com.mbobiosio.currencyconverter.network.safeApiCall
import com.mbobiosio.currencyconverter.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
class RepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : Repository {

    override fun listCurrencies(): Flow<ResourceState<CurrencyResponse>> = flow {
        emit(ResourceState.Loading)
        emit(
            safeApiCall {
                apiService.currencies(Constants.API_KEY)
            }
        )
    }.flowOn(Dispatchers.IO)

    override fun exchangeRates(from: String?, amount: Double): Flow<ResourceState<ConversionResponse>> = flow {
        emit(ResourceState.Loading)
        emit(
            safeApiCall {
                apiService.exchangeRates(Constants.API_KEY, from, amount)
            }
        )
    }

    override fun convertCurrencies(
        from: String?,
        to: String?,
        amount: Double
    ): Flow<ResourceState<ConversionResponse>> = flow {
        emit(ResourceState.Loading)
        emit(
            safeApiCall {
                apiService.convertCurrencies(Constants.API_KEY, from, to, amount)
            }
        )
    }.flowOn(Dispatchers.IO)
}
