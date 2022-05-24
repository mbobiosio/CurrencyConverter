package com.mbobiosio.currencyconverter.presentation.currencies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.mbobiosio.currencyconverter.data.local.entity.CurrencyResponse
import com.mbobiosio.currencyconverter.databinding.FragmentCurrenciesBinding
import com.mbobiosio.currencyconverter.domain.ResourceState
import com.mbobiosio.currencyconverter.domain.model.ConversionResponse
import com.mbobiosio.currencyconverter.domain.model.Currencies
import com.mbobiosio.currencyconverter.presentation.base.BaseBindingFragment
import com.mbobiosio.currencyconverter.util.* // ktlint-disable no-wildcard-imports
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
@AndroidEntryPoint
class CurrenciesFragment : BaseBindingFragment() {
    private var _binding: FragmentCurrenciesBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<CurrencyViewModel>()

    private var currencyCode: String? = "USD"

    private val currencyAdapter by lazy {
        CurrencyAdapter(viewModel)
    }

    override fun bindFragment(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentCurrenciesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun setupUI(view: View, savedInstanceState: Bundle?) {

        viewModel.listCurrencies()

        observeViewModel()

        with(binding) {
            currentRatesList.adapter = currencyAdapter

            amountInput.addTextChangedListener(
                afterTextChanged = {
                    it?.let {
                        when {
                            it.isNotEmpty() -> {
                                requestExchangeRates(currencyCode, it.toString())
                            }
                            else -> {
                                currencyAdapter.submitList(null)
                            }
                        }
                    }
                },
                onTextChanged = { string, _, _, _ ->
                    string?.let {
                        when {
                            it.isNotEmpty() -> {
                                requestExchangeRates(currencyCode, string.toString())
                            }
                            else -> {
                                currencyAdapter.submitList(null)
                            }
                        }
                    }
                }
            )

            currencies.apply {
                lifecycleOwner = this@CurrenciesFragment

                setOnSpinnerItemSelectedListener<String> { _, _, _, newItem ->
                    currencyCode = newItem
                    val amount = binding.amountInput.text.toString()
                    when {
                        amount.isNotEmpty() && amount != "0" -> {
                            requestExchangeRates(currencyCode, amount)
                        }
                    }
                }

                setOnSpinnerOutsideTouchListener { _, motionEvent ->
                    if (motionEvent.actionButton == 0) {
                        currencies.dismiss()
                    }
                }
            }

            amountInput.onAction(EditorInfo.IME_ACTION_DONE) {
                amountInput.hideKeyboard()
                Timber.d("Done")
            }
        }
    }

    private fun observeViewModel() {
        viewModel.currencyResult.observeOnce(viewLifecycleOwner) { result ->
            when (result) {
                is ResourceState.Loading -> {
                    Timber.d("Loading")
                }
                is ResourceState.Success -> {
                    updateSpinner(result.data)
                }
                is ResourceState.Error -> {
                    showError(result.response?.error?.message)
                }
                is ResourceState.NetworkError -> {
                    showError(result.error)
                }
            }
        }

        viewModel.exchangeRates.observeOnce(viewLifecycleOwner) { response ->
            when (response) {
                is ResourceState.Loading -> {
                    updateProgressUI()
                }
                is ResourceState.Success -> {
                    updateSuccessUI()

                    updateExchangeData(response.data)
                }

                is ResourceState.Error -> {
                    showError(response.response?.error?.message)
                }
                is ResourceState.NetworkError -> {
                    showError(response.error)
                }
            }
        }
    }

    private fun updateSpinner(data: List<CurrencyResponse>?) {
        data?.let {
            when {
                it.isNotEmpty() -> {
                    val currencies = it.map { map ->
                        map.currencies.keys
                    }.first().toList().sorted()

                    binding.currencies.setItems(currencies)
                }
            }
        }
    }

    private fun updateExchangeData(data: ConversionResponse?) {
        data?.let {
            val exchange = it.rates.map { entries ->
                Currencies(
                    entries.key,
                    entries.value.currencyName,
                    entries.value.rate,
                    entries.value.rateForAmount
                )
            }.sortedBy { currency ->
                currency.currency
            }

            // Set data to view
            binding.data = it

            // Submit list to adapter
            currencyAdapter.submitList(exchange)
        }
    }

    private fun requestExchangeRates(currency: String?, amount: String) {
        viewModel.getExchangeRates(currency, amount.toDouble())
        viewModel.currencyCode.value = currencyCode
    }

    private fun updateSuccessUI() {
        binding.apply {
            progress.gone()
            errorMessage.gone()
        }
    }

    private fun updateProgressUI() {
        binding.apply {
            // stop progress bar
            binding.progress.visible()

            // error message
            errorMessage.gone()
        }
    }

    private fun showError(message: String?) {
        binding.apply {
            // stop progress bar
            binding.progress.gone()

            // show error text
            errorMessage.visible()
            errorMessage.text = message
        }
    }

    override fun unbindFragment() {
        _binding = null
    }
}
