package dev.bogdanzurac.marp.feature.weather.domain

data class Forecast(
    val current: CurrentForecast,
    val weather: List<WeatherForecast>,
    val wind: Wind? = null,
    val iconUrl: String
)

data class CurrentForecast(
    val temp: Double,
    val tempMin: Double,
    val tempMax: Double,
    val feelsLike: Double,
    val pressure: Long,
    val humidity: Long,
)

data class WeatherForecast(
    val id: Long,
    val main: String,
    val description: String,
    val icon: String,
)

data class Wind(
    val speed: Double,
)
