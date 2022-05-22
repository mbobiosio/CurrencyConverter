package com.mbobiosio.currencyconverter.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mbobiosio.currencyconverter.TestData
import com.mbobiosio.currencyconverter.data.local.entity.CurrencyResponse
import com.mbobiosio.currencyconverter.data.remote.repository.Repository
import com.mbobiosio.currencyconverter.domain.ResourceState
import com.mbobiosio.currencyconverter.domain.model.ConversionResponse
import com.mbobiosio.currencyconverter.presentation.currencies.CurrencyViewModel
import io.mockk.mockk
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.* // ktlint-disable no-wildcard-imports
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
@ExperimentalCoroutinesApi
@OptIn(DelicateCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class CurrencyViewModelTest {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private val currencyRepository = mockk<Repository>()

    private lateinit var viewModel: CurrencyViewModel

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = CurrencyViewModel(currencyRepository)
    }

    @Test
    fun `test fetching currencies list`() = runTest {
        viewModel.listCurrencies()

        viewModel.currencyResult.observeForever { response ->
            if (viewModel.currencyResult.value != null) {
                when (response) {
                    is ResourceState.Success -> {
                        val result = CurrencyResponse(
                            id = null,
                            status = response.data[0].status,
                            currencies = response.data[0].currencies
                        )
                        Assert.assertEquals(result, TestData.getCurrencyListItems())

                        val currencies = viewModel.currencyResult.value
                        Assert.assertNotNull(currencies)
                        Assert.assertEquals(ResourceState.Success::class.java, result)
                        Assert.assertEquals(CurrencyResponse::class.java, currencies)
                    }
                    else -> {}
                }
            }
        }
    }

    @Test
    fun `test exchange rate success`() = runTest {
        viewModel.getExchangeRates("USD", 1.0)

        viewModel.exchangeRates.observeForever {
            if (viewModel.exchangeRates.value != null) {
                when (it) {
                    is ResourceState.Success -> {
                        val data = it.data
                        val result = ConversionResponse(
                            baseCurrencyCode = data.baseCurrencyCode,
                            baseCurrencyName = data.baseCurrencyCode,
                            amount = data.amount,
                            updatedDate = data.updatedDate,
                            rates = data.rates,
                            status = data.status
                        )
                    }
                    else -> {}
                }
            }
        }
    }

    @Test
    fun `test fetching exchange rates`() = runTest {
        viewModel.getExchangeRates("USD", 1.0)

        viewModel.exchangeRates.observeForever {
            if (viewModel.currencyResult.value != null) {
                when (it) {
                    is ResourceState.Success -> {
                        val data = it.data
                        val currency = ConversionResponse(
                            baseCurrencyCode = data.baseCurrencyCode,
                            baseCurrencyName = data.baseCurrencyCode,
                            amount = data.amount,
                            updatedDate = data.updatedDate,
                            rates = data.rates,
                            status = data.status
                        )

                        val exchangeRates = viewModel.exchangeRates.value
                        Assert.assertNotNull(exchangeRates)
                        Assert.assertEquals(exchangeRates, TestData.currencyObj)
                    }
                    else -> {}
                }
            }
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }
}
