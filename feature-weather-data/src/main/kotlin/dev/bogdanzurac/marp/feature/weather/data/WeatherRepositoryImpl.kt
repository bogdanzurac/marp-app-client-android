package dev.bogdanzurac.marp.feature.weather.data

import dev.bogdanzurac.marp.core.data.DataHolder
import dev.bogdanzurac.marp.core.services.Location
import dev.bogdanzurac.marp.feature.weather.domain.Forecast
import dev.bogdanzurac.marp.feature.weather.domain.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single
import kotlin.time.Duration.Companion.minutes

@Single
internal class WeatherRepositoryImpl(private val webService: WeatherWebService) : WeatherRepository {

    private lateinit var currentLocation: Location

    private val weatherHolder = DataHolder.Builder<Forecast>("weather")
        .fetcher { webService.getForecast(currentLocation).map { it.toForecast() } }
        .timeToLive(10.minutes)
        .build()

    override suspend fun getWeatherForecast(
        location: Location,
        refresh: Boolean
    ): Result<Forecast> = withContext(Dispatchers.IO) {
        currentLocation = location
        weatherHolder.get(refresh)
    }
}
