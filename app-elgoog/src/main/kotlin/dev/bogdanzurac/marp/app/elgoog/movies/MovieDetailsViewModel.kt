package dev.bogdanzurac.marp.app.elgoog.movies

import dev.bogdanzurac.marp.app.elgoog.core.ui.BaseViewModel
import dev.bogdanzurac.marp.app.elgoog.core.arch.DialogManager
import dev.bogdanzurac.marp.app.elgoog.core.ui.UiState
import dev.bogdanzurac.marp.app.elgoog.core.flowOf
import dev.bogdanzurac.marp.app.elgoog.core.foldResult
import dev.bogdanzurac.marp.app.elgoog.core.onFailure
import dev.bogdanzurac.marp.app.elgoog.core.ui.getGenericErrorDialogFor
import dev.bogdanzurac.marp.app.elgoog.movies.MovieDetailsViewModel.MovieDetailsUiState
import dev.bogdanzurac.marp.app.elgoog.movies.MovieDetailsViewModel.MovieDetailsUiState.*
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Factory

@Factory
internal class MovieDetailsViewModel(
    private val movieId: Long,
    private val dialogManager: DialogManager,
    private val repository: MoviesRepository,
) : BaseViewModel<MovieDetailsUiState>() {

    override val uiState: StateFlow<MovieDetailsUiState> =
        flowOf { repository.getMovie(movieId) }
            .onFailure { dialogManager.showDialog(getGenericErrorDialogFor(it)) }
            .foldResult({ Success(it) }, { Error(it) })
            .asState(Loading)

    internal sealed class MovieDetailsUiState : UiState {
        class Error(val exception: Throwable) : MovieDetailsUiState()
        class Success(val movie: MovieModel) : MovieDetailsUiState()
        object Loading : MovieDetailsUiState()
    }
}