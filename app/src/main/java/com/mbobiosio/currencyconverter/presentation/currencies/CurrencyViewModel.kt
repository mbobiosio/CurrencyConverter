package com.mbobiosio.currencyconverter.presentation.currencies

import androidx.lifecycle.* // ktlint-disable no-wildcard-imports
import com.mbobiosio.currencyconverter.data.local.entity.CurrencyResponse
import com.mbobiosio.currencyconverter.data.remote.repository.Repository
import com.mbobiosio.currencyconverter.domain.ResourceState
import com.mbobiosio.currencyconverter.domain.model.ConversionResponse
import com.mbobiosio.currencyconverter.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val currencyCode = MutableLiveData<String?>()

    private val _exchangeRates = SingleLiveEvent<ResourceState<ConversionResponse>>()
    val exchangeRates: LiveData<ResourceState<ConversionResponse>> get() = _exchangeRates

    private val _currencies: MutableLiveData<ResourceState<List<CurrencyResponse>>> =
        MutableLiveData()
    val currencyResult: LiveData<ResourceState<List<CurrencyResponse>>>
        get() = _currencies

    fun listCurrencies() = viewModelScope.launch {
        repository.listCurrencies().collect {
            _currencies.value = it
        }
    }

    fun getExchangeRates(from: String?, amount: Double) = viewModelScope.launch {
        repository.exchangeRates(from, amount).collect {
            _exchangeRates.value = it
        }
    }
}
