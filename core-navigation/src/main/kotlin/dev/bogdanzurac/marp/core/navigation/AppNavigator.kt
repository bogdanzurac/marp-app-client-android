package dev.bogdanzurac.marp.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import dev.bogdanzurac.marp.core.logger
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.*

abstract class AppNavigator {

    private var navController: NavController? = null

    private val currentRoute: MutableStateFlow<String> = MutableStateFlow("")

    val routes: Flow<String> = currentRoute

    init {
        observeRoutes()
    }

    fun onAttach(navController: NavController) {
        // If we were attached to another nav controller, make sure we detach from it
        if (this.navController != null && navController != this.navController)
            onDetach(navController)

        logger.d("Attaching to $navController")
        navController.addOnDestinationChangedListener(onDestinationChangedListener)
        this.navController = navController
    }

    fun onDetach(navController: NavController) {
        // Make sure we're only detaching from the intended nav controller
        if (this.navController == navController) {
            logger.d("Detaching from $navController")
            navController.removeOnDestinationChangedListener(onDestinationChangedListener)
            this.navController = null
        } else {
            logger.w("Failed to detach from $navController as it's already attached to ${this.navController}")
        }
    }

    protected abstract fun getFeatureNavigatorForRoute(route: String): FeatureNavigator

    fun navigateTo(action: NavigationAction) {
        if (action.route.path == currentRoute.value) return
        logger.d("AppNavigator navigate to: ${action.route.path}")
        if (action is BackAction) navController?.popBackStack()
        else navController?.navigate(
            route = action.route.path,
            navOptions = action.options?.let { options ->
                if (options.popUpToRoute == BackRoute.path) {
                    val startDestinationId = navController!!.graph.findStartDestination().id
                    options.copy(popUpToRoute = startDestinationId.toString()).toOptions()
                } else options.toOptions()
            })
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