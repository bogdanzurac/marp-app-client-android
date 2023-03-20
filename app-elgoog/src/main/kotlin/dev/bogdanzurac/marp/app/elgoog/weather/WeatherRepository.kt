package dev.bogdanzurac.marp.app.elgoog.weather

import dev.bogdanzurac.marp.app.elgoog.core.data.DataHolder
import dev.bogdanzurac.marp.app.elgoog.core.location.Location
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single
import kotlin.time.Duration.Companion.minutes

@Single
class WeatherRepository(private val webService: WeatherWebService) {

    private lateinit var currentLocation: Location

    private val weatherHolder = DataHolder.Builder<ForecastModel>("weather")
        .fetcher { webService.getForecast(currentLocation) }
        .timeToLive(10.minutes)
        .build()

    suspend fun getWeatherForecast(
        location: Location,
        refresh: Boolean = false
    ): Result<ForecastModel> = withContext(Dispatchers.IO) {
        currentLocation = location
        weatherHolder.get(refresh)
    }
}
