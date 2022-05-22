package com.mbobiosio.currencyconverter.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mbobiosio.currencyconverter.data.local.entity.CurrencyResponse
import com.mbobiosio.currencyconverter.data.remote.repository.Repository
import com.mbobiosio.currencyconverter.domain.ResourceState
import com.mbobiosio.currencyconverter.domain.model.ConversionResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _currencies: MutableLiveData<ResourceState<List<CurrencyResponse>>> =
        MutableLiveData()
    val currencyResult: LiveData<ResourceState<List<CurrencyResponse>>>
        get() = _currencies

    private val _conversion: MutableLiveData<ResourceState<ConversionResponse>> = MutableLiveData()
    val conversion: LiveData<ResourceState<ConversionResponse>>
        get() = _conversion

    fun listCurrencies() = viewModelScope.launch {
        repository.listCurrencies().collect {
            _currencies.value = it
        }
    }

    fun convertCurrency(from: String?, to: String?, amount: Double) =
        viewModelScope.launch {
            repository.convertCurrencies(from, to, amount).collect {
                _conversion.value = it
            }
        }
}
