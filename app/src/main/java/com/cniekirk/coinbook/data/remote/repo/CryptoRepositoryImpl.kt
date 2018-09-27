package com.cniekirk.coinbook.data.remote.repo

import com.cniekirk.coinbook.core.platform.NetworkHandler
import com.cniekirk.coinbook.data.remote.service.CryptoApi
import com.cniekirk.coinbook.utils.Either
import com.cniekirk.coinbook.utils.Either.Right
import com.cniekirk.coinbook.utils.Either.Left
import com.cniekirk.coinbook.utils.Failure
import com.cniekirk.coinbook.utils.Failure.ServerError
import com.cniekirk.coinbook.utils.Failure.NetworkConnection
import retrofit2.Call
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CryptoRepositoryImpl @Inject constructor(private val cryptoApi: CryptoApi,
                                               private val networkHandler: NetworkHandler) : CryptoRepository {

    override fun persistExchanges(): Either<Failure, Map<String, Map<String, List<String>>>> {

        return when(networkHandler.isConnected) {
            true -> request(cryptoApi.getAllExchanges(), {it}, emptyMap())
            false, null -> Left(NetworkConnection())
        }

    }

    private fun <T, R> request(call: Call<T>, transform: (T) -> R, default: T): Either<Failure, R> {
        return try {
            val response = call.execute()
            when (response.isSuccessful) {
                true -> Right(transform((response.body() ?: default)))
                false -> Left(ServerError())
            }
        } catch (exception: Throwable) {
            Left(ServerError())
        }
    }

}