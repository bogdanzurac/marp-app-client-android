package dev.bogdanzurac.marp.app.elgoog.weather

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastModel(
    @SerialName("main")
    val current: CurrentForecastModel,
    @SerialName("weather")
    val weather: List<WeatherForecastModel>,
    @SerialName("wind")
    val wind: WindModel? = null,
) {
    val iconUrl: String = "https://openweathermap.org/img/wn/${weather[0].icon}@4x.png"
}

@Serializable
data class CurrentForecastModel(
    @SerialName("temp")
    val temp: Double,
    @SerialName("temp_min")
    val tempMin: Double,
    @SerialName("temp_max")
    val tempMax: Double,
    @SerialName("feels_like")
    val feelsLike: Double,
    @SerialName("pressure")
    val pressure: Long,
    @SerialName("humidity")
    val humidity: Long,
)

@Serializable
data class WeatherForecastModel(
    @SerialName("id")
    val id: Long,
    @SerialName("main")
    val main: String,
    @SerialName("description")
    val description: String,
    @SerialName("icon")
    val icon: String,
)

@Serializable
data class WindModel(
    @SerialName("speed")
    val speed: Double,
)

internal fun Double.formatTemperature(): String = this.toInt().toString() + "° C"
