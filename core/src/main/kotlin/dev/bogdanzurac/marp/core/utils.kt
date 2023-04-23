package dev.bogdanzurac.marp.core

import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import java.util.*
import kotlin.Result.Companion.failure
import kotlin.time.Duration

fun randomUUID() = UUID.randomUUID().toString()

fun <T> repeatAsFlow(delay: Duration, action: suspend () -> T): Flow<T> =
    flow {
        while (currentCoroutineContext().isActive) {
            emit(action())
            delay(delay)
        }
    }

/**
 * Map each [Result] that is [Result.isSuccess] through the [mapping] lambda which returns a [Result],
 * while preserving the [Result.isFailure] results.
 *
 * Any exceptions that the [mapping] method may throw are not handled and instead are re-thrown upstream.
 */
suspend inline fun <R, T> Result<T>.flatMap(crossinline mapping: suspend (T) -> Result<R>): Result<R> =
    if (isSuccess) mapping(getOrNull() as T)
    else failure(exceptionOrNull()!!)

inline fun <R, T : R> Result<T>.recoverRethrow(transform: (exception: Throwable) -> Throwable): Result<R> {
    return when (val exception = exceptionOrNull()) {
        null -> this
        else -> failure(transform(exception))
    }
}


fun getLocale(): String = Locale.getDefault().language
