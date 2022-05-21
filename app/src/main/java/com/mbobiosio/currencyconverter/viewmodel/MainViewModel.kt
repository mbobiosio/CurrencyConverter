package com.mbobiosio.currencyconverter.viewmodel

import androidx.lifecycle.* // ktlint-disable no-wildcard-imports
import com.mbobiosio.currencyconverter.model.ConversionResponse
import com.mbobiosio.currencyconverter.model.CurrencyResponse
import com.mbobiosio.currencyconverter.network.ResourceState
import com.mbobiosio.currencyconverter.repository.DataStoreRepository
import com.mbobiosio.currencyconverter.repository.Repository
import com.mbobiosio.currencyconverter.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private var isLoading: Boolean = false

    fun getLoadingStatus(): Boolean {
        viewModelScope.launch {
            delay(1)
            isLoading = true
        }
        return isLoading
    }

    val currencyCode = MutableLiveData<String?>()

    val isFirstLaunch: LiveData<Boolean> = dataStoreRepository.getFirstLaunch().asLiveData()

    fun setFirstLaunch(isFirstLaunch: Boolean) =
        viewModelScope.launch {
            dataStoreRepository.saveFirstLaunch(isFirstLaunch)
        }

    private val _conversion: MutableLiveData<ResourceState<ConversionResponse>> = MutableLiveData()
    val conversion: LiveData<ResourceState<ConversionResponse>>
        get() = _conversion

    private val _currencies: MutableLiveData<ResourceState<CurrencyResponse>> = MutableLiveData()
    val currencies: LiveData<ResourceState<CurrencyResponse>>
        get() = _currencies

    private val _convert = SingleLiveEvent<ResourceState<ConversionResponse>>()
    val convert: LiveData<ResourceState<ConversionResponse>> get() = _convert

    fun convertCurrency(from: String?, to: String?, amount: Double) =
        viewModelScope.launch {
            repository.convertCurrencies(from, to, amount).collect {
                _conversion.value = it
            }
        }

    fun getExchangeRates(from: String?, amount: Double) = viewModelScope.launch {
        repository.exchangeRates(from, amount).collect {
            _convert.value = it
        }
    }

    fun listCurrencies() = viewModelScope.launch {
        repository.listCurrencies().collect {
            _currencies.value = it
        }
    }
}
