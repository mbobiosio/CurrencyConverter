package com.mbobiosio.currencyconverter

import com.mbobiosio.currencyconverter.data.local.entity.CurrencyResponse

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
object TestData {

    private val currencies = hashMapOf(
        "AED:" to "United Arab Emirates Dirham",
        "AFN:" to "Afghan Afghani"
    )

    val currencyObj: CurrencyResponse = CurrencyResponse(
        status = "success",
        currencies = currencies
    )

    fun getCurrencyListSuccessResponse(): CurrencyResponse =
        currencyObj

    fun getCurrencyListItems(): CurrencyResponse =
        currencyObj
}
