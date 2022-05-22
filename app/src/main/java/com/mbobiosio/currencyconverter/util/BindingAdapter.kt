package com.mbobiosio.currencyconverter.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.mbobiosio.currencyconverter.presentation.currencies.CurrencyViewModel

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
