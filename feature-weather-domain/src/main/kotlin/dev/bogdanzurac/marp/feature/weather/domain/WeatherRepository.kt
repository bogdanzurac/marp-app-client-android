package dev.bogdanzurac.marp.feature.weather.domain

import dev.bogdanzurac.marp.core.services.Location

interface WeatherRepository {

    suspend fun getWeatherForecast(
        location: Location,
        refresh: Boolean = false
    ): Result<Forecast>
}