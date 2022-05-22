package com.mbobiosio.currencyconverter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mbobiosio.currencyconverter.domain.repository.DataStoreRepository
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

    val isFirstLaunch: LiveData<Boolean> = dataStoreRepository.getFirstLaunch().asLiveData()

    fun setFirstLaunch(isFirstLaunch: Boolean) =
        viewModelScope.launch {
            dataStoreRepository.saveFirstLaunch(isFirstLaunch)
        }
}
