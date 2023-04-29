package dev.bogdanzurac.marp.app.elgoog.login

import androidx.lifecycle.viewModelScope
import dev.bogdanzurac.marp.app.elgoog.core.arch.DialogManager
import dev.bogdanzurac.marp.app.elgoog.core.ui.BaseViewModel
import dev.bogdanzurac.marp.app.elgoog.core.ui.Tracker
import dev.bogdanzurac.marp.app.elgoog.core.ui.UiState
import dev.bogdanzurac.marp.app.elgoog.login.LoginViewModel.LoginUiState
import dev.bogdanzurac.marp.app.elgoog.login.LoginViewModel.LoginUiState.Loading
import dev.bogdanzurac.marp.app.elgoog.login.LoginViewModel.LoginUiState.LogIn
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
    private val loginNavigator: LoginNavigator,
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
                .onSuccess { loginNavigator.navigateToMain() }
        }
    }

    override fun onSignupClicked() {
        loginNavigator.navigateToSignup()
    }
}

internal interface LoginUiEvents {
    fun onLoginClicked(email: String, password: String)
    fun onSignupClicked()
}