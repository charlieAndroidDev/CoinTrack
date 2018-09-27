package com.cniekirk.coinbook.di

import android.content.Context
import com.cniekirk.coinbook.CoinBookApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    ServiceModule::class,
    HomeActivityModule::class
])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Context) : Builder

        fun build() : AppComponent
    }

    fun inject(coinBookApp: CoinBookApp)

}