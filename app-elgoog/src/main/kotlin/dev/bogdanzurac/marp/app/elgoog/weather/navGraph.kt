package dev.bogdanzurac.marp.app.elgoog.weather

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation

fun NavGraphBuilder.weatherNavGraph() {
    navigation(
        startDestination = WeatherRoute.Overview.path,
        route = WeatherRoute.Root.path
    ) {
        composable(WeatherRoute.Overview.path) { WeatherScreen() }
    }
}