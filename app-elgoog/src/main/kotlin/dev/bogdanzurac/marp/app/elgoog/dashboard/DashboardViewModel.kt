package dev.bogdanzurac.marp.app.elgoog.dashboard

import dev.bogdanzurac.marp.app.elgoog.core.auth.AuthManager
import dev.bogdanzurac.marp.app.elgoog.core.auth.User
import dev.bogdanzurac.marp.core.feature.FeatureManager
import dev.bogdanzurac.marp.app.elgoog.core.navigation.*
import dev.bogdanzurac.marp.app.elgoog.core.ui.BaseViewModel
import dev.bogdanzurac.marp.app.elgoog.core.ui.UiState
import dev.bogdanzurac.marp.app.elgoog.dashboard.DashboardViewModel.DashboardUiState
import dev.bogdanzurac.marp.app.elgoog.dashboard.DashboardViewModel.DashboardUiState.LoadingFeatures
import dev.bogdanzurac.marp.app.elgoog.dashboard.DashboardViewModel.DashboardUiState.Success
import kotlinx.coroutines.flow.*
import org.koin.core.annotation.Factory

@Factory
internal class DashboardViewModel(
    private val appNavigator: AppNavigator,
    private val authManager: AuthManager,
    featureManager: FeatureManager
) : BaseViewModel<DashboardUiState>(), DashboardUiEvents {

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

    internal sealed class DashboardUiState : UiState {
        class Success(
            val items: List<ElgoogBottomNavigationItem>,
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