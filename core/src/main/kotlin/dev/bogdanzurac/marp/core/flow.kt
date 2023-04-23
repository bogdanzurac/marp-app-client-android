package dev.bogdanzurac.marp.core

import kotlinx.coroutines.flow.*

/**
 * Handle [Result.success] values inside the [action] lambda emitted on the current [Flow].
 */
inline fun <T> Flow<Result<T>>.onSuccess(crossinline action: suspend (value: T) -> Unit) =
    onEach { if (it.isSuccess) action(it.getOrNull() as T) }

/**
 * Handle [Result.failure] values inside the [action] lambda emitted on the current [Flow].
 */
inline fun <T> Flow<Result<T>>.onFailure(crossinline action: suspend (exception: Throwable) -> Unit) =
    onEach { result -> result.exceptionOrNull()?.let { action(it) } }


inline fun <R, T> Flow<Result<T>>.foldResult(
    crossinline onSuccess: (value: T) -> R,
    crossinline onFailure: (exception: Throwable) -> R
): Flow<R> =
    map { it.fold(onSuccess, onFailure) }

fun <T1, T2> Flow<Result<T1>>.combineResult(
    flow: Flow<Result<T2>>,
): Flow<Result<Pair<T1, T2>>> =
    combine(flow) { t1, t2 ->
        when {
            t1.isFailure -> Result.failure(t1.exceptionOrNull()!!)
            t2.isFailure -> Result.failure(t2.exceptionOrNull()!!)
            else -> Result.success(Pair(t1.getOrThrow(), t2.getOrThrow()))
        }
    }

fun <T1, T2, T3> Flow<Result<T1>>.combineResult(
    flow2: Flow<Result<T2>>,
    flow3: Flow<Result<T3>>,
): Flow<Result<Triple<T1, T2, T3>>> =
    combine(this, flow2, flow3) { t1, t2, t3 ->
        when {
            t1.isFailure -> Result.failure(t1.exceptionOrNull()!!)
            t2.isFailure -> Result.failure(t2.exceptionOrNull()!!)
            t3.isFailure -> Result.failure(t3.exceptionOrNull()!!)
            else -> Result.success(Triple(t1.getOrThrow(), t2.getOrThrow(), t3.getOrThrow()))
        }
    }

fun <T> Flow<T>.mapAsResult(): Flow<Result<T>> =
    map { Result.success(it) }


fun <T> flowOf(creator: suspend () -> T): Flow<T> =
    flow { emit(creator()) }

/**
 * Map each [Result] that is [Result.isSuccess] through the [mapping] lambda,
 * while preserving the [Result.isFailure] results.
 *
 * Any exceptions that the [mapping] method may throw are not handled and instead are re-thrown upstream.
 */
inline fun <R, T> Flow<Result<T>>.mapResult(crossinline mapping: suspend (T) -> R): Flow<Result<R>> =
    map { result ->
        if (result.isSuccess) Result.success(mapping(result.getOrNull() as T))
        else Result.failure(result.exceptionOrNull()!!)
    }

/**
 * Map each [Result] that is [Result.isSuccess] through the [mapping] lambda,
 * while preserving the [Result.isFailure] results.
 *
 * Any exceptions that the [mapping] method may throw are not handled and instead are re-thrown upstream.
 */
inline fun <R, T> Flow<Result<T>>.mapResultCatching(crossinline mapping: suspend (T) -> R): Flow<Result<R>> =
    map { result ->
        if (result.isSuccess) runCatching { mapping(result.getOrNull() as T) }
        else Result.failure(result.exceptionOrNull()!!)
    }

/**
 * Map each [Result] that is [Result.isSuccess] through the [mapping] lambda,
 * while preserving the [Result.isFailure] results.
 *
 * Any exceptions that the [mapping] method may throw are not handled and instead are re-thrown upstream.
 */
inline fun <R, T> Flow<Result<T>>.flatMap(crossinline mapping: suspend (T) -> Result<R>): Flow<Result<R>> =
    map { result ->
        if (result.isSuccess) mapping(result.getOrNull() as T)
        else Result.failure(result.exceptionOrNull()!!)
    }