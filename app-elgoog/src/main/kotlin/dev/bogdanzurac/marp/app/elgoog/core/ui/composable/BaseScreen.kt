package dev.bogdanzurac.marp.app.elgoog.core.ui.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.bogdanzurac.marp.app.elgoog.core.ui.BaseViewModel
import dev.bogdanzurac.marp.app.elgoog.core.ui.UiState

@Composable
fun <S : UiState> BaseScreen(
    viewModel: BaseViewModel<S>,
    content: @Composable (State<S>) -> Unit,
) {
    content(viewModel.uiState.collectAsStateWithLifecycle())
}