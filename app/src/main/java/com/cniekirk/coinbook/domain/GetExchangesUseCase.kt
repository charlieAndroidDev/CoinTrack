package com.cniekirk.coinbook.domain

import com.cniekirk.coinbook.data.remote.repo.CryptoRepository
import javax.inject.Inject

class GetExchangesUseCase @Inject constructor(private val cryptoRepository: CryptoRepository) :
        BaseUseCase<Map<String, Map<String, List<String>>>, BaseUseCase.None>() {

    override suspend fun run(params: None) = cryptoRepository.persistExchanges()

}