package dev.bogdanzurac.marp.feature.auth.ui

import androidx.lifecycle.viewModelScope
import dev.bogdanzurac.marp.core.prompts.DialogManager
import dev.bogdanzurac.marp.core.ui.BaseViewModel
import dev.bogdanzurac.marp.core.ui.Tracker
import dev.bogdanzurac.marp.core.ui.UiState
import dev.bogdanzurac.marp.feature.auth.ui.LoginViewModel.LoginUiState
import dev.bogdanzurac.marp.feature.auth.ui.LoginViewModel.LoginUiState.Loading
import dev.bogdanzurac.marp.feature.auth.ui.LoginViewModel.LoginUiState.LogIn
import dev.bogdanzurac.marp.core.auth.AuthManager
import dev.bogdanzurac.marp.core.logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory

@Factory
internal class LoginViewModel(
    private val authManager: AuthManager,
    private val dialogManager: DialogManager,
    private val authNavigator: AuthNavigator,
    private val tracker: Tracker,
) : BaseViewModel<LoginUiState>(), LoginUiEvents {

    private val loadingState: MutableStateFlow<Boolean> = MutableStateFlow(false)

    override val uiState: StateFlow<LoginUiState> =
        loadingState
            .onStart { tracker.trackScreen(LOGIN_SCREEN) }
            .map { if (it) Loading else LogIn }
            .asState(LogIn)

    internal sealed class LoginUiState : UiState {
        object LogIn : LoginUiState()
        object Loading : LoginUiState()
    }

    override fun onLoginClicked(email: String, password: String) {
        viewModelScope.launch {
            loadingState.tryEmit(true)
            authManager.loginUser(email, password)
                .onFailure {
                    loadingState.tryEmit(false)
                    logger.e("Could not log in", it)
                    dialogManager.showDialog(getAuthErrorDialogFor(it))
                }
                .onSuccess { authNavigator.navigateToMain() }
        }
    }

    override fun onSignupClicked() {
        authNavigator.navigateToSignup()
    }
}

internal interface LoginUiEvents {
    fun onLoginClicked(email: String, password: String)
    fun onSignupClicked()
}