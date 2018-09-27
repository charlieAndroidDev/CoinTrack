package com.cniekirk.coinbook.core.platform

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cniekirk.coinbook.utils.Failure

/**
 * Base [ViewModel] class to handle a [Failure]
 * **/
abstract class BaseViewModel : ViewModel() {

    val failure: MutableLiveData<Failure> = MutableLiveData()

    protected fun handleFailure(failure: Failure) {
        this.failure.value = failure
    }

}