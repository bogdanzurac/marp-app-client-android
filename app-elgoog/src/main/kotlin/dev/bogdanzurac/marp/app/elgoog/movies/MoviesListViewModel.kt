package dev.bogdanzurac.marp.app.elgoog.movies

import dev.bogdanzurac.marp.core.prompts.DialogManager
import dev.bogdanzurac.marp.core.ui.BaseViewModel
import dev.bogdanzurac.marp.core.ui.Tracker
import dev.bogdanzurac.marp.core.ui.UiState
import dev.bogdanzurac.marp.core.ui.getGenericErrorDialogFor
import dev.bogdanzurac.marp.app.elgoog.movies.MoviesListViewModel.MoviesListUiState
import dev.bogdanzurac.marp.app.elgoog.movies.MoviesListViewModel.MoviesListUiState.*
import dev.bogdanzurac.marp.core.flowOf
import dev.bogdanzurac.marp.core.foldResult
import dev.bogdanzurac.marp.core.logger
import dev.bogdanzurac.marp.core.onFailure
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import org.koin.core.annotation.Factory

@Factory
internal class MoviesListViewModel(
    private val dialogManager: DialogManager,
    private val navigator: MoviesNavigator,
    private val moviesRepository: MoviesRepository,
    private val tracker: Tracker,
) : BaseViewModel<MoviesListUiState>(), MoviesListUiEvents {

    override val uiState: StateFlow<MoviesListUiState> =
        flowOf { moviesRepository.getTrendingMovies() }
            .onStart { tracker.trackScreen(MOVIES_LIST_SCREEN) }
            .onFailure {
                logger.e("Could not get movie list", it)
                dialogManager.showDialog(getGenericErrorDialogFor(it))
            }
            .foldResult({ Success(it) }, { Error(it) })
            .asState(Loading)

    internal sealed class MoviesListUiState : UiState {
        class Error(val exception: Throwable) : MoviesListUiState()
        class Success(val movies: List<MovieModel>) : MoviesListUiState()
        object Loading : MoviesListUiState()
    }

    override fun onMovieClicked(id: Long) {
        navigator.navigateToMovieDetails(id)
    }
}

internal interface MoviesListUiEvents {
    fun onMovieClicked(id: Long)
}