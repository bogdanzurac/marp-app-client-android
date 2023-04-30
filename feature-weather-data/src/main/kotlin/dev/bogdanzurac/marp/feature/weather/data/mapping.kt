package dev.bogdanzurac.marp.feature.weather.data

import dev.bogdanzurac.marp.feature.weather.domain.CurrentForecast
import dev.bogdanzurac.marp.feature.weather.domain.Forecast
import dev.bogdanzurac.marp.feature.weather.domain.WeatherForecast
import dev.bogdanzurac.marp.feature.weather.domain.Wind

internal fun ForecastModel.toForecast(): Forecast =
    Forecast(
        current.toCurrentForecast(),
        weather.map { it.toWeatherForecast() },
        wind?.toWind(),
        iconUrl
    )

internal fun CurrentForecastModel.toCurrentForecast(): CurrentForecast =
    CurrentForecast(temp, tempMin, tempMax, feelsLike, pressure, humidity)

internal fun WeatherForecastModel.toWeatherForecast(): WeatherForecast =
    WeatherForecast(id, main, description, icon)

internal fun WindModel.toWind(): Wind = Wind(speed)

