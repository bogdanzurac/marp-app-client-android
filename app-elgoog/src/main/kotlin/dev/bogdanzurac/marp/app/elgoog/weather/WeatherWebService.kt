package dev.bogdanzurac.marp.app.elgoog.weather

import WEATHER_API_KEY
import dev.bogdanzurac.marp.app.elgoog.core.location.Location
import dev.bogdanzurac.marp.app.elgoog.core.ws.MockNetworkDelayFeature
import dev.bogdanzurac.marp.app.elgoog.core.ws.MockNetworkErrorsFeature
import dev.bogdanzurac.marp.app.elgoog.core.ws.WebService
import dev.bogdanzurac.marp.core.feature.FeatureManager
import dev.bogdanzurac.marp.core.getLocale
import io.ktor.client.request.*
import org.koin.core.annotation.Single

/**
 * https://openweathermap.org/current
 */
@Single
class WeatherWebService(featureManager: FeatureManager) : WebService(
    "https://api.openweathermap.org",
    featureManager.isEnabled(MockNetworkDelayFeature),
    featureManager.isEnabled(MockNetworkErrorsFeature)
) {

    override val requestBuilder: HttpRequestBuilder.() -> Unit = {
        parameter("appid", WEATHER_API_KEY)
        parameter("lang", getLocale())
    }

    suspend fun getForecast(location: Location): Result<ForecastModel> =
        get("/data/2.5/weather") {
            parameter("lat", location.lat)
            parameter("lon", location.long)
            parameter("units", "metric")
        }
}
