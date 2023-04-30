package dev.bogdanzurac.marp.feature.weather.ui

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