package com.mbobiosio.currencyconverter.util

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.mbobiosio.currencyconverter.domain.model.Rates
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
fun convertRates(rates: Map<String, Rates>?): Double? {
    return rates?.let {
        it.values.lastOrNull()?.rateForAmount ?: 0.0
    }
}

fun convertRateToString(rate: Double?): String {
    return String.format("%, .2f", rate)
}

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(
        lifecycleOwner,
        object : Observer<T> {
            override fun onChanged(t: T) {
                observer.onChanged(t)
                removeObserver(this)
            }
        }
    )
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun Context.toast(message: String, length: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, length).show()
}

@Suppress("DEPRECATION")
fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    return true
                }
            }
        }
    } else {
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
            return true
        }
    }
    return false
}

fun View.hideKeyboard() {
    val inputMethodManager = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}

fun EditText.onAction(action: Int, runAction: () -> Unit) {
    this.setOnEditorActionListener { _, actionId, _ ->
        return@setOnEditorActionListener when (actionId) {
            action -> {
                runAction.invoke()
                true
            }
            else -> false
        }
    }
}

/**
 * [Moshi] extension to transform a [Map] to Json
 * */
inline fun <reified T, reified K> Moshi.mapToJson(data: Map<T, K>): String =
    adapter<Map<T, K>>(
        Types.newParameterizedType(
            MutableMap::class.java,
            T::class.java,
            K::class.java
        )
    ).toJson(data)

/**
 * [Moshi] extension to transform a json to [Map]
 * */
inline fun <reified T, reified K> Moshi.jsonToMap(json: String): Map<T, K>? =
    adapter<Map<T, K>>(
        Types.newParameterizedType(
            MutableMap::class.java,
            T::class.java,
            K::class.java
        )
    ).fromJson(json)

/*** Returns a new [MultiTypeBuilder] for the specified data class type.*/
//inline fun <reified T : Any, reified E : Enum<E>> create(
//   noinline computeViewType: ComputeViewType<T, E>
//): MultiTypeBuilder<T, E> {
//   return MultiTypeBuilder(T::class, computeViewType, fragment)
//}
