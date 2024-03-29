package dev.bogdanzurac.marp.feature.weather.ui

import dev.bogdanzurac.marp.feature.weather.ui.WeatherViewModel.WeatherUiState
import dev.bogdanzurac.marp.feature.weather.ui.WeatherViewModel.WeatherUiState.*
import dev.bogdanzurac.marp.core.flowOf
import dev.bogdanzurac.marp.core.foldResult
import dev.bogdanzurac.marp.core.logger
import dev.bogdanzurac.marp.core.onFailure
import dev.bogdanzurac.marp.core.prompts.DialogManager
import dev.bogdanzurac.marp.core.services.getLocationErrorDialogFor
import dev.bogdanzurac.marp.core.ui.BaseViewModel
import dev.bogdanzurac.marp.core.ui.Tracker
import dev.bogdanzurac.marp.core.ui.UiState
import dev.bogdanzurac.marp.feature.weather.domain.Forecast
import dev.bogdanzurac.marp.feature.weather.domain.GetWeatherForecastUseCase
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import org.koin.core.annotation.Factory

@Factory
internal class WeatherViewModel(
    private val dialogManager: DialogManager,
    private val getWeatherForecastUseCase: GetWeatherForecastUseCase,
    private val tracker: Tracker,
) : BaseViewModel<WeatherUiState>() {

    override val uiState: StateFlow<WeatherUiState> =
        flowOf { getWeatherForecastUseCase() }
            .onStart { tracker.trackScreen(WEATHER_SCREEN) }
            .onFailure {
                logger.e("Could not get weather", it)
                dialogManager.showDialog(getLocationErrorDialogFor(it))
            }
            .foldResult({ Success(it) }, { Error(it) })
            .asState(Loading)

    internal sealed class WeatherUiState : UiState {
        class Error(val exception: Throwable) : WeatherUiState()
        class Success(val forecast: Forecast) : WeatherUiState()
        object Loading : WeatherUiState()
    }
}