package dev.bogdanzurac.marp.app.elgoog.core

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

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
inline fun <R, T> Flow<Result<T>>.flatMap(crossinline mapping: suspend (T) -> Result<R>): Flow<Result<R>> =
    map { result ->
        if (result.isSuccess) mapping(result.getOrNull() as T)
        else Result.failure(result.exceptionOrNull()!!)
    }