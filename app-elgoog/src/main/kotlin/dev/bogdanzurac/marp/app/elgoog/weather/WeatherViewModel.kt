package dev.bogdanzurac.marp.app.elgoog.weather

import dev.bogdanzurac.marp.app.elgoog.core.ui.BaseViewModel
import dev.bogdanzurac.marp.app.elgoog.core.arch.DialogManager
import dev.bogdanzurac.marp.app.elgoog.core.ui.UiState
import dev.bogdanzurac.marp.app.elgoog.core.flowOf
import dev.bogdanzurac.marp.app.elgoog.core.foldResult
import dev.bogdanzurac.marp.app.elgoog.core.location.getLocationErrorDialogFor
import dev.bogdanzurac.marp.app.elgoog.core.onFailure
import dev.bogdanzurac.marp.app.elgoog.weather.WeatherViewModel.WeatherUiState
import dev.bogdanzurac.marp.app.elgoog.weather.WeatherViewModel.WeatherUiState.*
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Factory

@Factory
internal class WeatherViewModel(
    private val dialogManager: DialogManager,
    private val getWeatherForecastUseCase: GetWeatherForecastUseCase,
) : BaseViewModel<WeatherUiState>() {

    override val uiState: StateFlow<WeatherUiState> =
        flowOf { getWeatherForecastUseCase() }
            .onFailure { dialogManager.showDialog(getLocationErrorDialogFor(it)) }
            .foldResult({ Success(it) }, { Error(it) })
            .asState(Loading)

    internal sealed class WeatherUiState : UiState {
        class Error(val exception: Throwable) : WeatherUiState()
        class Success(val forecast: ForecastModel) : WeatherUiState()
        object Loading : WeatherUiState()
    }
}