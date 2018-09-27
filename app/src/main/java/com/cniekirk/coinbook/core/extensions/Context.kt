package com.cniekirk.coinbook.core.extensions

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo

val Context.networkInfo: NetworkInfo? get() =
    (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo

internal inline fun SharedPreferences.apply(func:
SharedPreferences.Editor.() -> SharedPreferences.Editor) {
    this.edit().func().apply()
}