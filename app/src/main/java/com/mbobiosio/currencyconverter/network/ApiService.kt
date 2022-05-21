package com.mbobiosio.currencyconverter.network

import com.mbobiosio.currencyconverter.model.ConversionResponse
import com.mbobiosio.currencyconverter.model.CurrencyResponse
import com.mbobiosio.currencyconverter.model.ExchangeResponse
import com.mbobiosio.currencyconverter.util.Constants
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
interface ApiService {

    @GET(Constants.CONVERT)
    suspend fun convertCurrencies(
        @Query("api_key") access_key: String,
        @Query("from") from: String?,
        @Query("to") to: String?,
        @Query("amount") amount: Double
    ): ConversionResponse

    @GET(Constants.CONVERT)
    suspend fun exchangeRates(
        @Query("api_key") access_key: String,
        @Query("from") from: String?,
        @Query("amount") amount: Double
    ): ConversionResponse

    @GET(Constants.LIST)
    suspend fun currencies(
        @Query("api_key") apiKey: String
    ): CurrencyResponse
}
