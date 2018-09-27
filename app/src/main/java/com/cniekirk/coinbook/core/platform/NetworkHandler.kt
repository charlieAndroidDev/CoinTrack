package com.cniekirk.coinbook.core.platform

import android.content.Context
import com.cniekirk.coinbook.core.extensions.networkInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkHandler
@Inject constructor(private val context: Context) {
    val isConnected get() = context.networkInfo?.isConnected
}