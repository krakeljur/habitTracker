package com.example.habittracker.model

typealias Mapper<Input, Output> = (Input) -> Output

sealed class WorkResult<T> {

    class LoadingResult<T>() : WorkResult<T>()

    data class SuccessResult<T> (
        val data: T
    ) : WorkResult<T>()

    data class ErrorResult<T>(
        val error: Exception
    ) : WorkResult<T>()

    fun <R>map(mapper: Mapper<T, R>? = null): WorkResult<R> = when(this) {
        is LoadingResult -> LoadingResult()
        is ErrorResult -> ErrorResult(this.error)
        is SuccessResult -> {
            if (mapper == null) throw IllegalStateException("Mapper should be !! for Success Result")
            SuccessResult(mapper(this.data))
        }

    }

}


