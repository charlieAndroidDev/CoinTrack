package com.cniekirk.coinbook.di

import com.cniekirk.coinbook.BuildConfig
import com.cniekirk.coinbook.data.remote.repo.CryptoRepository
import com.cniekirk.coinbook.data.remote.repo.CryptoRepositoryImpl
import com.cniekirk.coinbook.data.remote.service.CryptoApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class ServiceModule {

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit) : CryptoApi = retrofit.create(CryptoApi::class.java)

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit = Retrofit.Builder()
            .baseUrl("https://min-api.cryptocompare.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun provideOkHttp() : OkHttpClient {

        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }

        return okHttpClientBuilder.build()

    }

    @Provides
    @Singleton
    fun provideCryptoRepository(cryptoRepository: CryptoRepositoryImpl): CryptoRepository = cryptoRepository

}