package dev.bogdanzurac.marp.feature.weather.ui

import dev.bogdanzurac.marp.feature.weather.domain.CurrentForecast
import dev.bogdanzurac.marp.feature.weather.domain.Forecast
import dev.bogdanzurac.marp.feature.weather.domain.WeatherForecast
import dev.bogdanzurac.marp.feature.weather.domain.Wind

val composeWeatherForecastModelPreview =
    Forecast(
        CurrentForecast(
            temp = 23.2,
            tempMin = 21.0,
            tempMax = 26.5,
            feelsLike = 24.0,
            pressure = 800,
            humidity = 68,
        ),
        MutableList(3) {
            WeatherForecast(
                id = 5,
                main = "Clouds",
                description = "broken clouds",
                icon = "04d"
            )
        },
        Wind(24.5),
        "abc"
    )