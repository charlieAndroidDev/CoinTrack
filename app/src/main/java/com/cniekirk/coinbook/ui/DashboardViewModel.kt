package com.cniekirk.coinbook.ui

import androidx.lifecycle.MutableLiveData
import com.cniekirk.coinbook.core.platform.BaseViewModel
import com.cniekirk.coinbook.domain.GetExchangesUseCase
import com.cniekirk.coinbook.domain.BaseUseCase.None
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
        private val getExchangesUseCase: GetExchangesUseCase): BaseViewModel() {

    // Observed by the view
    val exchanges: MutableLiveData<Map<String, Map<String, List<String>>>> = MutableLiveData()

    // Executed by the view
    fun loadExchanges() = getExchangesUseCase(None()) { it.either(::handleFailure, ::handleExchangeMap) }

    private fun handleExchangeMap(exchanges: Map<String, Map<String, List<String>>>) {

        this.exchanges.value = exchanges

    }
}