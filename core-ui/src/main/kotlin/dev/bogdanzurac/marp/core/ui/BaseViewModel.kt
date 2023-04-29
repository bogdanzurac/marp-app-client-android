package dev.bogdanzurac.marp.core.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.bogdanzurac.marp.core.logger
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlin.time.Duration.Companion.seconds

abstract class BaseViewModel<State : UiState> : ViewModel() {

    init {
        logger.d("${this::class.simpleName} created")
    }

    abstract val uiState: StateFlow<State>

    override fun onCleared() {
        super.onCleared()
        logger.d("${this::class.simpleName} destroyed")
    }

    protected fun <T> Flow<T>.asState(defaultState: T): StateFlow<T> =
        stateIn(viewModelScope, WhileSubscribed(1.seconds.inWholeMilliseconds), defaultState)

    protected fun <T> Flow<T>.shared(): SharedFlow<T> =
        shareIn(viewModelScope, WhileSubscribed(1.seconds.inWholeMilliseconds), 1)
}