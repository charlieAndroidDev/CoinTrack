package com.cniekirk.coinbook

import android.app.Activity
import android.app.Application
import com.cniekirk.coinbook.di.AppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class CoinBookApp : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        AppInjector.init(this)

    }

    override fun activityInjector() = dispatchingAndroidInjector

}