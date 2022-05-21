package com.mbobiosio.currencyconverter.presentation.currencies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mbobiosio.currencyconverter.databinding.ItemCurrencyBinding
import com.mbobiosio.currencyconverter.model.Currencies
import com.mbobiosio.currencyconverter.viewmodel.MainViewModel

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
class CurrencyAdapter(
    private val viewModel: MainViewModel
) :
    ListAdapter<Currencies, CurrencyAdapter.CurrencyViewHolder>(ItemCallback()) {

    private class ItemCallback : DiffUtil.ItemCallback<Currencies>() {
        override fun areItemsTheSame(oldItem: Currencies, newItem: Currencies): Boolean =
            oldItem.currencyName == newItem.currencyName && oldItem.currency == newItem.currency

        override fun areContentsTheSame(oldItem: Currencies, newItem: Currencies): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val binding =
            ItemCurrencyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrencyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, viewModel)
        }
    }

    inner class CurrencyViewHolder(
        private val binding: ItemCurrencyBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: Currencies,
            mainViewModel: MainViewModel
        ) {
            with(binding) {
                currency = item
                viewModel = mainViewModel
                executePendingBindings()
            }
        }
    }
}
