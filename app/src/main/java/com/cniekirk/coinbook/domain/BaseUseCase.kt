package com.cniekirk.coinbook.domain

import com.cniekirk.coinbook.utils.Either
import com.cniekirk.coinbook.utils.Failure
import kotlinx.coroutines.experimental.IO
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch

abstract class BaseUseCase<out Type, in Params> where Type : Any {

    abstract suspend fun run(params: Params): Either<Failure, Type>

    operator fun invoke(params: Params, onResult: (Either<Failure, Type>) -> Unit = {}) {

        val job = async(IO) { run(params) }
        launch(UI) { onResult.invoke(job.await()) }

    }

    class None

}