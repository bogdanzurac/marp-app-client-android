package dev.bogdanzurac.marp.app.elgoog.core.navigation

fun NavOptions.toOptions(): androidx.navigation.NavOptions =
    androidx.navigation.NavOptions.Builder()
        .also { it.setLaunchSingleTop(singleTop) }
        .also { options ->
            popUpToRoute?.let {
                options.setPopUpTo(popUpToRoute, popUpToInclusive)
            }
        }
        .build()