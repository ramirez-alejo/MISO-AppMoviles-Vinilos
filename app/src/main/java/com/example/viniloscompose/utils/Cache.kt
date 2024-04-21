package com.example.viniloscompose.utils

abstract class Cache(
    private val exceptionHandler: IExceptionHandler
) {
    protected suspend fun <T> runQuery(query: suspend () -> T) =
        query.runCatching { invoke() }
            .onFailure { exceptionHandler.handle(it) }
            .getOrNull()
}