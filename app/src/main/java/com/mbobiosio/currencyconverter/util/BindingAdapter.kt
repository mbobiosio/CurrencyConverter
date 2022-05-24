package com.mbobiosio.currencyconverter.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.mbobiosio.currencyconverter.R
import com.mbobiosio.currencyconverter.presentation.currencies.CurrencyViewModel
import java.text.SimpleDateFormat
import java.util.*

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
@BindingAdapter("convertDouble")
fun TextView.convertDouble(value: Double) {
    val rate = convertRateToString(value)
    text = rate
}

@BindingAdapter("code", "exchangeRate", "currency")
fun TextView.exchangeRate(code: CurrencyViewModel, ex: Double, currency: String) {
    text = "1 ".plus(code.currencyCode.value).plus(" = ").plus(ex).plus(" ").plus(currency)
}

@BindingAdapter("lastUpdated")
fun TextView.lastUpdated(date: String?) {
    date?.let {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        formatter.parse(it)?.also { formatted ->
            val finalFormatter = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
            text = context.getString(R.string.last_updated, finalFormatter.format(formatted))
        }
    }
}
