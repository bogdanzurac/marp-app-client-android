package dev.bogdanzurac.marp.app.elgoog.core.navigation

import androidx.navigation.NavController
import dev.bogdanzurac.marp.app.elgoog.core.logger
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.*

abstract class AppNavigator {

    private var navController: NavController? = null

    private val currentRoute: MutableStateFlow<String> = MutableStateFlow("")

    init {
        observeRoutes()
    }

    fun onCreate(navController: NavController) {
        if (this.navController != null) return
        this.navController = navController
        navController.addOnDestinationChangedListener(onDestinationChangedListener)
    }

    fun onDestroy() {
        navController?.removeOnDestinationChangedListener(onDestinationChangedListener)
        navController = null
    }

    protected abstract fun getFeatureNavigatorForRoute(route: String): FeatureNavigator

    fun navigateTo(action: NavigationAction) {
        if (action.route.path == currentRoute.value) return
        logger.d("AppNavigator navigate to: ${action.route.path}")
        if (action is BackAction) navController?.popBackStack()
        else navController?.navigate(action.route.path, action.options?.toOptions())
    }

    private fun observeRoutes() {
        currentRoute
            .filterNot { it.isEmpty() }
            .flatMapLatest { route ->
                // Whenever the nav graph changes, attach to the corresponding navigator
                logger.d("Current route changed to: $route")
                val navigator = getFeatureNavigatorForRoute(route)
                logger.d("Switching active navigator to: ${navigator::class.simpleName}")
                navigator.observeRoutes()
            }
            .onEach { navigateTo(it) }
            .launchIn(MainScope())
    }

    private val onDestinationChangedListener =
        NavController.OnDestinationChangedListener { _, destination, _ ->
            // If the current nav graph changed, notify listeners
            if (destination.parent?.route != currentRoute.value) {
                logger.d("Navigation route changed: ${destination.route}")
                currentRoute.tryEmit(destination.parent!!.route!!)
            }
        }
}