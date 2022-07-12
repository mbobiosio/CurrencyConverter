package com.mbobiosio.currencyconverter.data.remote.repository

import androidx.room.withTransaction
import com.mbobiosio.currencyconverter.data.local.AppDatabase
import com.mbobiosio.currencyconverter.data.local.entity.CurrencyResponse
import com.mbobiosio.currencyconverter.data.remote.api.ApiService
import com.mbobiosio.currencyconverter.domain.ResourceState
import com.mbobiosio.currencyconverter.domain.model.ConversionResponse
import com.mbobiosio.currencyconverter.domain.safeApiCall
import com.mbobiosio.currencyconverter.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
class RepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val appDatabase: AppDatabase
) : Repository {

    private val currencyDao = appDatabase.currencyDao

    override suspend fun insertCurrency(currency: CurrencyResponse) =
        currencyDao.insertCurrency(currency)

    override suspend fun deleteItem(currency: CurrencyResponse) =
        currencyDao.deleteCurrency(currency)

    override suspend fun deleteAll() = currencyDao.deleteAll()

    override suspend fun getAllCurrencies(): ResourceState<List<CurrencyResponse>> =
        ResourceState.Success(currencyDao.getAllCurrencies())

    override suspend fun listCurrencies(): Flow<ResourceState<List<CurrencyResponse>>> = flow {
        emit(getAllCurrencies())
        emit(ResourceState.Loading)
        when (
            val response = safeApiCall {
                apiService.currencies(Constants.API_KEY)
            }
        ) {
            is ResourceState.Loading -> {
                Timber.d("Loading")
                emit(ResourceState.Loading)
            }
            is ResourceState.Success -> {
                appDatabase.withTransaction {
                    val data = response.data
                    data?.let {
                        currencyDao.deleteAll()
                        currencyDao.insertCurrency(it)
                    }
                }
                emit(ResourceState.Success(currencyDao.getAllCurrencies()))
                Timber.d("${currencyDao.getAllCurrencies()}")
            }
            is ResourceState.Error -> {
                Timber.d("Error ${response.response?.error?.message}")
                emit(ResourceState.Error(response.code, response.response))
            }
            is ResourceState.NetworkError -> {
                Timber.d("Network ${response.error}")
                emit(ResourceState.NetworkError(response.error))
            }
        }
    }.flowOn(Dispatchers.IO)

    override fun exchangeRates(
        from: String?,
        amount: Double
    ): Flow<ResourceState<ConversionResponse>> = flow {
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
