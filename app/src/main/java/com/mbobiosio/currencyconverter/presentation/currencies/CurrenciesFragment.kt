package com.mbobiosio.currencyconverter.presentation.currencies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.mbobiosio.currencyconverter.databinding.FragmentCurrenciesBinding
import com.mbobiosio.currencyconverter.model.Currencies
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
class CurrenciesFragment : BaseBindingFragment() {
    private var _binding: FragmentCurrenciesBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<MainViewModel>()

    private var currencyCode: String? = "USD"

    private val currencyAdapter by lazy {
        CurrencyAdapter(viewModel)
    }

    override fun bindFragment(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentCurrenciesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun setupUI(view: View, savedInstanceState: Bundle?) {

        observeViewModel()

        with(binding) {
            currentRatesList.adapter = currencyAdapter

            amountInput.addTextChangedListener(
                afterTextChanged = {
                    it?.let {
                        when {
                            it.isNotEmpty() -> {
                                requestExchangeRates(it.toString())
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
                                requestExchangeRates(string.toString())
                            }
                            else -> {
                                currencyAdapter.submitList(null)
                            }
                        }
                    }
                }
            )

            amountInput.onAction(EditorInfo.IME_ACTION_DONE) {
                amountInput.hideKeyboard()
                Timber.d("Done")
            }
        }
    }

    private fun observeViewModel() {
        viewModel.convert.observeOnce(viewLifecycleOwner) { response ->
            when (response) {
                is ResourceState.Loading -> {
                    updateProgressUI()
                }
                is ResourceState.Success -> {
                    updateSuccessUI()

                    val exchange = response.data.rates.map { entries ->
                        Currencies(
                            entries.key,
                            entries.value.currency_name,
                            entries.value.rate,
                            entries.value.rate_for_amount
                        )
                    }.sortedBy { currency ->
                        currency.currency
                    }
                    currencyAdapter.submitList(exchange)
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

    private fun requestExchangeRates(amount: String) {
        viewModel.getExchangeRates(currencyCode, amount.toDouble())
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
