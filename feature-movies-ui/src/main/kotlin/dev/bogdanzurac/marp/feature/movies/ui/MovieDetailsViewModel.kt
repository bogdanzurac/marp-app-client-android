package dev.bogdanzurac.marp.feature.movies.ui

import dev.bogdanzurac.marp.feature.movies.ui.MovieDetailsViewModel.MovieDetailsUiState
import dev.bogdanzurac.marp.feature.movies.ui.MovieDetailsViewModel.MovieDetailsUiState.*
import dev.bogdanzurac.marp.core.flowOf
import dev.bogdanzurac.marp.core.foldResult
import dev.bogdanzurac.marp.core.logger
import dev.bogdanzurac.marp.core.onFailure
import dev.bogdanzurac.marp.core.prompts.DialogManager
import dev.bogdanzurac.marp.core.ui.BaseViewModel
import dev.bogdanzurac.marp.core.ui.Tracker
import dev.bogdanzurac.marp.core.ui.UiState
import dev.bogdanzurac.marp.core.ui.getGenericErrorDialogFor
import dev.bogdanzurac.marp.feature.movies.domain.Movie
import dev.bogdanzurac.marp.feature.movies.domain.MoviesRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import org.koin.core.annotation.Factory

@Factory
internal class MovieDetailsViewModel(
    private val movieId: Long,
    private val dialogManager: DialogManager,
    private val repository: MoviesRepository,
    private val tracker: Tracker,
) : BaseViewModel<MovieDetailsUiState>() {

    override val uiState: StateFlow<MovieDetailsUiState> =
        flowOf { repository.getMovie(movieId) }
            .onStart { tracker.trackScreen(MOVIES_DETAILS_SCREEN, movieId.toString()) }
            .onFailure {
                logger.e("Could not get movie details", it)
                dialogManager.showDialog(getGenericErrorDialogFor(it))
            }
            .foldResult({ Success(it) }, { Error(it) })
            .asState(Loading)

    internal sealed class MovieDetailsUiState : UiState {
        class Error(val exception: Throwable) : MovieDetailsUiState()
        class Success(val movie: Movie) : MovieDetailsUiState()
        object Loading : MovieDetailsUiState()
    }
}