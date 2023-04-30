package dev.bogdanzurac.marp.feature.weather.data

import dev.bogdanzurac.marp.core.feature.FeatureManager
import dev.bogdanzurac.marp.core.getLocale
import dev.bogdanzurac.marp.core.services.Location
import dev.bogdanzurac.marp.core.ws.MockNetworkDelayFeature
import dev.bogdanzurac.marp.core.ws.MockNetworkErrorsFeature
import dev.bogdanzurac.marp.core.ws.WebService
import io.ktor.client.request.*
import org.koin.core.annotation.Property
import org.koin.core.annotation.Single

/**
 * https://openweathermap.org/current
 */
@Single
class WeatherWebService(
    @Property(WEATHER_KEY) val apiKey: String,
    featureManager: FeatureManager
) : WebService(
    "https://api.openweathermap.org",
    featureManager.isEnabled(MockNetworkDelayFeature),
    featureManager.isEnabled(MockNetworkErrorsFeature),
) {

    override val requestBuilder: HttpRequestBuilder.() -> Unit = {
        parameter("appid", apiKey)
        parameter("lang", getLocale())
    }

    suspend fun getForecast(location: Location): Result<ForecastModel> =
        get("/data/2.5/weather") {
            parameter("lat", location.lat)
            parameter("lon", location.long)
            parameter("units", "metric")
        }

    companion object {
        const val WEATHER_KEY = "weather_api_key"
    }
}
