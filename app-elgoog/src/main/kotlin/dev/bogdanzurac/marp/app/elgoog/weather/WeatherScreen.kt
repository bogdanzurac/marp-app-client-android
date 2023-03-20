package dev.bogdanzurac.marp.app.elgoog.weather

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.bogdanzurac.marp.app.elgoog.R
import dev.bogdanzurac.marp.app.elgoog.core.theme.ElgoogTheme
import dev.bogdanzurac.marp.app.elgoog.core.ui.composable.BaseScreen
import dev.bogdanzurac.marp.app.elgoog.core.ui.composable.EmptyView
import dev.bogdanzurac.marp.app.elgoog.core.ui.composable.LoadingView
import dev.bogdanzurac.marp.app.elgoog.weather.WeatherViewModel.WeatherUiState.*
import org.koin.androidx.compose.koinViewModel
import java.util.*

@Composable
internal fun WeatherScreen(viewModel: WeatherViewModel = koinViewModel()) =
    BaseScreen(viewModel) { state ->
        when (val uiState = state.value) {
            is Loading -> LoadingView()
            is Error -> EmptyView()
            is Success -> WeatherView(uiState.forecast)
        }
    }

@Composable
private fun WeatherView(forecast: ForecastModel) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Row(modifier = Modifier
            .height(IntrinsicSize.Min)
            .padding(top = 16.dp)) {
            AsyncImage(
                model = forecast.iconUrl,
                modifier = Modifier.fillMaxWidth(0.5f),
                contentDescription = forecast.weather[0].main,
                contentScale = ContentScale.FillWidth,
            )
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxHeight()
            ) {
                Text(
                    text = stringResource(R.string.temperature_current)
                            + " " + forecast.current.temp.formatTemperature(),
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = forecast.current.tempMin.formatTemperature() + "/"
                            + forecast.current.tempMax.formatTemperature(),
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = stringResource(
                        R.string.temperature_feels_like,
                        forecast.current.feelsLike.formatTemperature()
                    ),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
        Spacer(modifier = Modifier.size(32.dp))
        forecast.weather.forEach {
            Text(
                text = it.main + " - " + it.description.capitalize(Locale.getDefault()),
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text(
                text = stringResource(R.string.atmospheric_pressure),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = forecast.current.pressure.toString() + "mbar",
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text(
                text = stringResource(R.string.humidity),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = forecast.current.humidity.toString() + "%",
                style = MaterialTheme.typography.bodyLarge
            )
        }
        forecast.wind?.let {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text(
                    text = stringResource(R.string.wind_speed),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = it.speed.toString() + " km/h",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
@Preview
private fun WeatherPreview() {
    ElgoogTheme {
        WeatherView(composeWeatherForecastModelPreview)
    }
}