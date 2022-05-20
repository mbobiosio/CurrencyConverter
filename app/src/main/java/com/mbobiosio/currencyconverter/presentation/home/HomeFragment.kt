package com.mbobiosio.currencyconverter.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mbobiosio.currencyconverter.databinding.FragmentHomeBinding
import com.mbobiosio.currencyconverter.network.ResourceState
import com.mbobiosio.currencyconverter.presentation.base.BaseBindingFragment
import com.mbobiosio.currencyconverter.util.* // ktlint-disable no-wildcard-imports
import com.mbobiosio.currencyconverter.viewmodel.MainViewModel
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

    private val viewModel by viewModels<MainViewModel>()

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
                    amount.isEmpty() or (amount == "0") -> {
                        Timber.d("Empty amount")
                        activity?.toast("Empty amount")
                    }
                    !requireContext().isNetworkAvailable() -> {
                        Timber.d("No network")
                    }
                    else -> {
                        doConversion(sourceCurrencyCode, newCurrencyCode)
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

        viewModel.currencies.observe(this) { result ->
            when (result) {
                is ResourceState.Loading -> {
                    binding.progress.visible()
                }
                is ResourceState.Success -> {
                    // stop progress bar
                    binding.progress.gone()
                    // show button
                    binding.btnConvert.visible()

                    val currencies = result.data.currencies
                    val sortCurrencies = currencies.keys.sorted()

                    with(binding) {
                        sourceCurrencies.setItems(sortCurrencies)
                        newCurrency.setItems(sortCurrencies)
                    }
                }
                is ResourceState.Error -> {
                    Timber.d("Error ${result.response?.error?.message}")
                }
                is ResourceState.NetworkError -> {
                    Timber.d("Network Error ${result.error}")
                }
            }
        }
    }

    private fun doConversion(fromCurrency: String?, toCurrency: String?) {

        hideKeyboard(requireActivity())

        // make progress bar visible
        binding.progress.visible()

        // make button invisible
        binding.btnConvert.gone()

        // Get the data inputted
        val amount = binding.amount.text.toString().toDouble()

        viewModel.convertCurrency(fromCurrency, toCurrency, amount)

        observeConversion()
    }

    private fun observeConversion() {
        viewModel.response.observe(this) {
            when (it) {
                is ResourceState.Success -> {

                    val rateForAmount = convertRates(it.data.rates)

                    // set the value in the second edit text field
                    binding.rate.text = convertRateToString(rateForAmount)

                    // stop progress bar
                    binding.progress.gone()
                    // show button
                    binding.btnConvert.visible()
                }
                is ResourceState.Error -> {
                    // stop progress bar
                    binding.progress.gone()
                    // show button
                    binding.btnConvert.visible()
                    Timber.d("Error ${it.response?.error?.message}")
                }

                is ResourceState.Loading -> {
                    Timber.d("Loading")
                    // stop progress bar
                    binding.progress.visible()
                    // show button
                    binding.btnConvert.gone()
                }
                is ResourceState.NetworkError -> {
                    // stop progress bar
                    binding.progress.gone()
                    // show button
                    binding.btnConvert.visible()

                    Timber.d("Network ${it.error}")
                }
            }
        }
    }
}
