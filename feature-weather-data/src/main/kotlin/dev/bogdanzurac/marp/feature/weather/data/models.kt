package dev.bogdanzurac.marp.feature.weather.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ForecastModel(
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
internal data class CurrentForecastModel(
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
internal data class WeatherForecastModel(
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
internal data class WindModel(
    @SerialName("speed")
    val speed: Double,
)
