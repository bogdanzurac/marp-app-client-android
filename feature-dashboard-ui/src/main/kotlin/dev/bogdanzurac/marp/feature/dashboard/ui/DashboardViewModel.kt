package dev.bogdanzurac.marp.feature.dashboard.ui

import dev.bogdanzurac.marp.core.auth.AuthManager
import dev.bogdanzurac.marp.core.auth.User
import dev.bogdanzurac.marp.core.feature.FeatureManager
import dev.bogdanzurac.marp.core.navigation.*
import dev.bogdanzurac.marp.core.ui.BaseViewModel
import dev.bogdanzurac.marp.core.ui.UiState
import dev.bogdanzurac.marp.feature.dashboard.ui.DashboardViewModel.DashboardUiState
import dev.bogdanzurac.marp.feature.dashboard.ui.DashboardViewModel.DashboardUiState.LoadingFeatures
import dev.bogdanzurac.marp.feature.dashboard.ui.DashboardViewModel.DashboardUiState.Success
import kotlinx.coroutines.flow.*

abstract class DashboardViewModel(
    private val appNavigator: AppNavigator,
    private val authManager: AuthManager,
    featureManager: FeatureManager
) : BaseViewModel<DashboardUiState>(), DashboardUiEvents {

    abstract val bottomNavigationItems: List<BottomNavigationItem>

    private val selectedItemPosition: Flow<Int> =
        appNavigator.routes
            .map { route ->
                currentRoute = route
                bottomNavigationItems.indexOfFirst { it.route.path == route }
            }

    private var currentRoute: String = ""

    override val uiState: StateFlow<DashboardUiState> =
        featureManager.isReady()
            .filter { it }
            .flatMapConcat { authManager.observeUser() }
            .combine(selectedItemPosition) { user, selectedItemPosition ->
                Success(
                    items = bottomNavigationItems,
                    selectedItemPosition = selectedItemPosition,
                    isBottomNavigationBarVisible = selectedItemPosition != -1,
                    user = user
                )
            }
            .asState(LoadingFeatures)

    sealed class DashboardUiState : UiState {
        class Success(
            val items: List<BottomNavigationItem>,
            val selectedItemPosition: Int,
            val isBottomNavigationBarVisible: Boolean = true,
            val user: User? = null
        ) : DashboardUiState() {
            val isUserLoggedIn = user != null
        }

        object LoadingFeatures : DashboardUiState()
    }

    override fun onRouteClicked(route: AppRoute) {
        appNavigator.navigateTo(
            NavigationAction(
                route, NavOptions(
                    singleTop = true,
                    popUpToRoute = BackRoute.path,
                    popUpToShouldSaveState = true,
                    restoreState = true
                )
            )
        )
    }

    override fun onLogoutClicked() {
        authManager.signOutUser()
    }
}

internal interface DashboardUiEvents {
    fun onRouteClicked(route: AppRoute)
    fun onLogoutClicked()
}