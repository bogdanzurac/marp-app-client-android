package dev.bogdanzurac.marp.core.navigation

open class NavigationAction(
    val route: AppRoute,
    val options: NavOptions? = null
)