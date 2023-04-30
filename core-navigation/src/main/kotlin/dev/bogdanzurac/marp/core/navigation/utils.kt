package dev.bogdanzurac.marp.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation

fun NavGraphBuilder.navigation(
    startDestination: AppRoute,
    route: AppRoute,
    builder: NavGraphBuilder.() -> Unit
) = navigation(
    startDestination = startDestination.path,
    route = route.path,
    builder = builder,
)

fun NavGraphBuilder.composable(
    route: AppRoute,
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(route = route.path, content = content)
}

fun NavGraphBuilder.composable(
    route: AppRoute.ArgsRoute,
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(
        route = route.path,
        arguments = route.args.map { it.toNavigationArgument() },
        content = content
    )
}

fun NavBackStackEntry.getLongArg(name: String): Long? =
    arguments?.getLong(name)

fun NavBackStackEntry.getStringArg(name: String): String? =
    arguments?.getString(name)
