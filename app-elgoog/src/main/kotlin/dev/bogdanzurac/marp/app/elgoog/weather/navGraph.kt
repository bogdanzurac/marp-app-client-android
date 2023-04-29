package dev.bogdanzurac.marp.app.elgoog.weather

import androidx.navigation.NavGraphBuilder
import dev.bogdanzurac.marp.core.navigation.composable
import dev.bogdanzurac.marp.core.navigation.navigation

fun NavGraphBuilder.weatherNavGraph() {
    navigation(
        startDestination = WeatherOverview,
        route = Weather
    ) {
        composable(WeatherOverview) { WeatherScreen() }
    }
}