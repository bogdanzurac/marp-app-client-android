package dev.bogdanzurac.marp.app.elgoog.core.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.bogdanzurac.marp.app.elgoog.core.logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
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
}