package com.mbobiosio.currencyconverter.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mbobiosio.currencyconverter.data.local.entity.CurrencyResponse
import com.mbobiosio.currencyconverter.databinding.FragmentHomeBinding
import com.mbobiosio.currencyconverter.domain.ResourceState
import com.mbobiosio.currencyconverter.presentation.base.BaseBindingFragment
import com.mbobiosio.currencyconverter.util.* // ktlint-disable no-wildcard-imports
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
@AndroidEntryPoint
class HomeFragment : BaseBindingFragment() {

    private var sourceCurrencyCode: String? = "USD"
    private var newCurrencyCode: String? = "USD"

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel>()

    override fun bindFragment(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUI(view: View, savedInstanceState: Bundle?) {

        fetchCurrencies()

        with(binding) {
            sourceCurrencies.apply {
                lifecycleOwner = this@HomeFragment

                setOnSpinnerItemSelectedListener<String> { _, _, _, newItem ->
                    sourceCurrencyCode = newItem
                }

                setOnSpinnerOutsideTouchListener { _, motionEvent ->
                    if (motionEvent.actionButton == 0) {
                        sourceCurrencies.dismiss()
                    }
                }
            }

            newCurrency.apply {
                lifecycleOwner = this@HomeFragment

                setOnSpinnerItemSelectedListener<String> { _, _, _, newItem ->
                    newCurrencyCode = newItem
                }

                setOnSpinnerOutsideTouchListener { _, motionEvent ->
                    if (motionEvent.actionButton == 0) {
                        newCurrency.dismiss()
                    }
                }
            }

            btnConvert.setOnClickListener {
                val amount = amount.text.toString()
                when {
                    amount.isEmpty() || (amount == "0") -> {
                        activity?.toast("Invalid amount")
                    }
                    !requireContext().isNetworkAvailable() -> {
                        Timber.d("No network")
                        activity?.toast("No Internet Connection!!!")
                    }
                    else -> {
                        doConversion(sourceCurrencyCode, newCurrencyCode, amount)
                    }
                }
            }
        }
    }

    override fun unbindFragment() {
        _binding = null
    }

    private fun fetchCurrencies() {
        viewModel.listCurrencies()

        viewModel.currencyResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResourceState.Loading -> {
                    updateProgressUI()
                }
                is ResourceState.Success -> {
                    updateSuccessUI()

                    updateData(result.data)
                }
                is ResourceState.Error -> {
                    showError(result.response?.error?.message)
                }
                is ResourceState.NetworkError -> {
                    showError(result.error)
                }
            }
        }
    }

    private fun updateData(data: List<CurrencyResponse>?) {
        data?.let {
            when {
                it.isNotEmpty() -> {
                    val currencies = it.map { response ->
                        response.currencies.keys
                    }.first().toList().sorted()

                    with(binding) {
                        sourceCurrencies.setItems(currencies)
                        newCurrency.setItems(currencies)
                    }
                }
            }
        }
    }

    private fun doConversion(fromCurrency: String?, toCurrency: String?, amount: String) {

        binding.apply {
            // hide keyboard
            btnConvert.hideKeyboard()
            // make progress bar visible
            progress.visible()

            // make button invisible
            btnConvert.gone()

            viewModel.convertCurrency(fromCurrency, toCurrency, amount.toDouble())
        }

        observeConversion()
    }

    private fun observeConversion() {
        viewModel.conversion.observe(this) {
            when (it) {
                is ResourceState.Loading -> {
                    updateProgressUI()
                }

                is ResourceState.Success -> {
                    updateSuccessUI()

                    val rateForAmount = convertRates(it.data?.rates)
                    binding.rate.text = convertRateToString(rateForAmount)
                }
                is ResourceState.Error -> {
                    showError(it.response?.error?.message)
                }

                is ResourceState.NetworkError -> {
                    showError(it.error)
                }
            }
        }
    }

    private fun updateSuccessUI() {
        binding.apply {
            progress.gone()
            btnConvert.visible()
            errorMessage.gone()
        }
    }

    private fun updateProgressUI() {
        binding.apply {
            // stop progress bar
            progress.visible()
            // show button
            btnConvert.gone()
            // error message
            errorMessage.gone()
        }
    }

    private fun showError(message: String?) {
        binding.apply {
            // stop progress bar
            progress.gone()
            // show button
            btnConvert.visible()

            // show error text
            errorMessage.visible()
            errorMessage.text = message
        }
    }
}
