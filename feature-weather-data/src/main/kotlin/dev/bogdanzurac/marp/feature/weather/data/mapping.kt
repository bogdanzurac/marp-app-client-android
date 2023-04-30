package dev.bogdanzurac.marp.app.elgoog.weather

import dev.bogdanzurac.marp.feature.weather.data.CurrentForecastModel
import dev.bogdanzurac.marp.feature.weather.data.ForecastModel
import dev.bogdanzurac.marp.feature.weather.data.WeatherForecastModel
import dev.bogdanzurac.marp.feature.weather.data.WindModel
import dev.bogdanzurac.marp.feature.weather.domain.CurrentForecast
import dev.bogdanzurac.marp.feature.weather.domain.Forecast
import dev.bogdanzurac.marp.feature.weather.domain.WeatherForecast
import dev.bogdanzurac.marp.feature.weather.domain.Wind

fun ForecastModel.toForecast(): Forecast =
    Forecast(
        current.toCurrentForecast(),
        weather.map { it.toWeatherForecast() },
        wind?.toWind(),
        iconUrl
    )

fun CurrentForecastModel.toCurrentForecast(): CurrentForecast =
    CurrentForecast(temp, tempMin, tempMax, feelsLike, pressure, humidity)

fun WeatherForecastModel.toWeatherForecast(): WeatherForecast =
    WeatherForecast(id, main, description, icon)

fun WindModel.toWind(): Wind = Wind(speed)

