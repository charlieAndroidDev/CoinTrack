package com.cniekirk.coinbook.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cniekirk.coinbook.ui.DashboardViewModel
import com.cniekirk.coinbook.viewmodel.CoinBookViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(DashboardViewModel::class)
    abstract fun bindDashboardViewModel(dashboardViewModel: DashboardViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: CoinBookViewModelFactory): ViewModelProvider.Factory

}