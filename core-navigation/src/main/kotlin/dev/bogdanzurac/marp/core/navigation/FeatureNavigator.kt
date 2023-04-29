package dev.bogdanzurac.marp.core.navigation

import dev.bogdanzurac.marp.core.logger
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.onEach

open class FeatureNavigator {

    private val routesFlow: MutableSharedFlow<NavigationAction> =
        MutableSharedFlow(
            replay = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST,
            extraBufferCapacity = 1
        )

    fun navigateBack() {
        routesFlow.tryEmit(BackAction)
    }

    protected fun navigateTo(route: AppRoute, navOptions: NavOptions? = null) {
        logger.d("Feature navigator navigating to route: ${route.path}")
        routesFlow.tryEmit(NavigationAction(route, navOptions))
    }

    fun observeRoutes(): Flow<NavigationAction> = routesFlow
        .onEach { routesFlow.resetReplayCache() }
}