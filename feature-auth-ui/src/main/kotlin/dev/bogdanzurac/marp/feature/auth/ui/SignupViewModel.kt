package dev.bogdanzurac.marp.feature.auth.ui

import androidx.lifecycle.viewModelScope
import dev.bogdanzurac.marp.core.prompts.DialogManager
import dev.bogdanzurac.marp.core.ui.BaseViewModel
import dev.bogdanzurac.marp.core.ui.Tracker
import dev.bogdanzurac.marp.core.ui.UiState
import dev.bogdanzurac.marp.feature.auth.ui.SignupViewModel.SignupUiState
import dev.bogdanzurac.marp.feature.auth.ui.SignupViewModel.SignupUiState.Loading
import dev.bogdanzurac.marp.feature.auth.ui.SignupViewModel.SignupUiState.SignUp
import dev.bogdanzurac.marp.core.auth.AuthManager
import dev.bogdanzurac.marp.core.logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory

@Factory
internal class SignupViewModel(
    private val authManager: AuthManager,
    private val dialogManager: DialogManager,
    private val authNavigator: AuthNavigator,
    private val tracker: Tracker,
) : BaseViewModel<SignupUiState>(), SignupUiEvents {

    private val loadingState: MutableStateFlow<Boolean> = MutableStateFlow(false)

    override val uiState: StateFlow<SignupUiState> =
        loadingState
            .onStart { tracker.trackScreen(SIGNUP_SCREEN) }
            .map { if (it) Loading else SignUp }
            .asState(SignUp)

    internal sealed class SignupUiState : UiState {
        object SignUp : SignupUiState()
        object Loading : SignupUiState()
    }

    override fun onSignupClicked(email: String, password: String) {
        viewModelScope.launch {
            loadingState.tryEmit(true)
            authManager.signupUser(email, password)
                .onFailure {
                    loadingState.tryEmit(false)
                    logger.e("Could not sign up", it)
                    dialogManager.showDialog(getAuthErrorDialogFor(it))
                }
                .onSuccess { authNavigator.navigateToMain() }
        }
    }
}

internal interface SignupUiEvents {
    fun onSignupClicked(email: String, password: String)
}