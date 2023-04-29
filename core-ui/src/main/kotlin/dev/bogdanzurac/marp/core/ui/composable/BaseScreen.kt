package dev.bogdanzurac.marp.core.ui.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.bogdanzurac.marp.core.ui.BaseViewModel
import dev.bogdanzurac.marp.core.ui.UiState

@Composable
fun <S : UiState> BaseScreen(
    viewModel: BaseViewModel<S>,
    content: @Composable (State<S>) -> Unit,
) {
    content(viewModel.uiState.collectAsStateWithLifecycle())
}