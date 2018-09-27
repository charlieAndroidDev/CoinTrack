package com.cniekirk.coinbook.data.remote.repo

import com.cniekirk.coinbook.utils.Either
import com.cniekirk.coinbook.utils.Failure

interface CryptoRepository {

    fun persistExchanges() : Either<Failure, Map<String, Map<String, List<String>>>>

}