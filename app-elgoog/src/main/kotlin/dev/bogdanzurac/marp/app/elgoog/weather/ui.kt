package dev.bogdanzurac.marp.app.elgoog.weather

val composeWeatherForecastModelPreview =
    ForecastModel(
        CurrentForecastModel(
            temp = 23.2,
            tempMin = 21.0,
            tempMax = 26.5,
            feelsLike = 24.0,
            pressure = 800,
            humidity = 68,
        ),
        MutableList(3) {
            WeatherForecastModel(
                id = 5,
                main = "Clouds",
                description = "broken clouds",
                icon = "04d"
            )
        },
        WindModel(24.5),
    )