package dev.bogdanzurac.marp.app.elgoog.weather

import dev.bogdanzurac.marp.app.elgoog.core.navigation.AppRoute

sealed class WeatherRoute(override val path: String) : AppRoute {
    object Root : WeatherRoute("weather")
    object Overview : WeatherRoute("weather/overview")
}
