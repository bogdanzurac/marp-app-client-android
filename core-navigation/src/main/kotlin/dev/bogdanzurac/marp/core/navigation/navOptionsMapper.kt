package dev.bogdanzurac.marp.core.navigation

fun NavOptions.toOptions(): androidx.navigation.NavOptions =
    androidx.navigation.NavOptions.Builder()
        .also {
            it.setLaunchSingleTop(singleTop)
            it.setRestoreState(restoreState)
        }
        .also { options ->
            popUpToRoute?.let {
                popUpToRoute.toIntOrNull()?.let {
                    options.setPopUpTo(it, popUpToInclusive, popUpToShouldSaveState)
                } ?: options.setPopUpTo(popUpToRoute, popUpToInclusive, popUpToShouldSaveState)
            }
        }
        .build()