package com.cniekirk.coinbook.data.remote.service

import retrofit2.Call
import retrofit2.http.GET

interface CryptoApi {

    @GET("data/all/exchanges")
    fun getAllExchanges() : Call<Map<String, Map<String, List<String>>>>

}