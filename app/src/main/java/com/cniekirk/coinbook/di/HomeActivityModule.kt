package com.cniekirk.coinbook.di

import com.cniekirk.coinbook.activites.HomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeActivityModule {

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeHomeActivity(): HomeActivity

}