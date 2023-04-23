package dev.bogdanzurac.marp.app.elgoog.weather

import dev.bogdanzurac.marp.core.flatMap
import dev.bogdanzurac.marp.app.elgoog.core.location.LocationProvider
import org.koin.core.annotation.Factory

@Factory
class GetWeatherForecastUseCase(
    private val locationProvider: LocationProvider,
    private val weatherRepository: WeatherRepository
) {

    suspend operator fun invoke(): Result<ForecastModel> =
        locationProvider.getLocation()
            .flatMap { weatherRepository.getWeatherForecast(it) }
}