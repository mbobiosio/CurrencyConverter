package com.mbobiosio.currencyconverter.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mbobiosio.currencyconverter.TestData
import com.mbobiosio.currencyconverter.data.local.AppDatabase
import com.mbobiosio.currencyconverter.data.remote.api.ApiService
import com.mbobiosio.currencyconverter.data.remote.repository.RepositoryImpl
import com.mbobiosio.currencyconverter.util.Constants
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.* // ktlint-disable no-wildcard-imports
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
@RunWith(MockitoJUnitRunner::class)
class RepositoryTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val currencyApi = mock<ApiService>()

    private val appDatabase = mock<AppDatabase>()

    private lateinit var repository: RepositoryImpl

    private val mainThread = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThread)
        repository = RepositoryImpl(currencyApi, appDatabase)
    }

    @Test
    fun `test get currencies`() = runBlocking {
        Mockito.`when`(currencyApi.currencies(Constants.API_KEY))
            .thenReturn(TestData.getCurrencyListSuccessResponse())

        val result = repository.listCurrencies().toList()

        Assert.assertNotNull(result)
        // Assert.assertEquals(result.last(), TestData.getCurrencyListSuccessResponse())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThread.cleanupTestCoroutines()
    }
}
