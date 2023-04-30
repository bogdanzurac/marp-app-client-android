package dev.bogdanzurac.marp.feature.weather.domain

import dev.bogdanzurac.marp.core.flatMap
import dev.bogdanzurac.marp.core.services.LocationProvider
import dev.bogdanzurac.marp.feature.weather.domain.Forecast
import dev.bogdanzurac.marp.feature.weather.domain.WeatherRepository
import org.koin.core.annotation.Factory

@Factory
class GetWeatherForecastUseCase(
    private val locationProvider: LocationProvider,
    private val weatherRepository: WeatherRepository
) {

    suspend operator fun invoke(): Result<Forecast> =
        locationProvider.getLocation()
            .flatMap { weatherRepository.getWeatherForecast(it) }
}