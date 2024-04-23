package com.example.viniloscompose.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import com.example.viniloscompose.utils.UseCase.Result

abstract class FlowUseCase<in TParam, out TResult>(
    private val exceptionHandler: IExceptionHandler,
    private val dispatcher: CoroutineDispatcher
) {

    @ExperimentalCoroutinesApi
    @Suppress("TooGenericExceptionCaught")
    suspend operator fun invoke(param: TParam) =
        performAction(param)
            .catch { exception ->
                exceptionHandler.handle(exception)
                emit(Result.Failure(exception))
            }
            .flowOn(dispatcher)

    protected abstract suspend fun performAction(param: TParam): Flow<Result<TResult>>
}