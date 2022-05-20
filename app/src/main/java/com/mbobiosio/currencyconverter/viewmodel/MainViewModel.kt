package com.mbobiosio.currencyconverter.viewmodel

import androidx.lifecycle.* // ktlint-disable no-wildcard-imports
import com.mbobiosio.currencyconverter.model.ConversionResponse
import com.mbobiosio.currencyconverter.model.CurrencyResponse
import com.mbobiosio.currencyconverter.network.ResourceState
import com.mbobiosio.currencyconverter.repository.DataStoreRepository
import com.mbobiosio.currencyconverter.repository.Repository
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

    var isLoading: Boolean = false

    fun getLoadingStatus(): Boolean {
        viewModelScope.launch {
            delay(1)
            isLoading = true
        }
        return isLoading
    }

    val isFirstLaunch: LiveData<Boolean> = dataStoreRepository.getFirstLaunch().asLiveData()

    fun setFirstLaunch(isFirstLaunch: Boolean) =
        viewModelScope.launch {
            dataStoreRepository.saveFirstLaunch(isFirstLaunch)
        }

    private val _response: MutableLiveData<ResourceState<ConversionResponse>> = MutableLiveData()
    val response: LiveData<ResourceState<ConversionResponse>> get() = _response

    private val _currencies: MutableLiveData<ResourceState<CurrencyResponse>> = MutableLiveData()
    val currencies: LiveData<ResourceState<CurrencyResponse>> get() = _currencies

    fun convertCurrency(from: String?, to: String?, amount: Double) =
        viewModelScope.launch {
            repository.convertCurrencies(from, to, amount).collect {
                _response.value = it
            }
        }

    fun listCurrencies() = viewModelScope.launch {
        repository.listCurrencies().collect {
            _currencies.value = it
        }
    }
}
