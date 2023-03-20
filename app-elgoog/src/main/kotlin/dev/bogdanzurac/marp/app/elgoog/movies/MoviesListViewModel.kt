package dev.bogdanzurac.marp.app.elgoog.movies

import dev.bogdanzurac.marp.app.elgoog.core.ui.BaseViewModel
import dev.bogdanzurac.marp.app.elgoog.core.arch.DialogManager
import dev.bogdanzurac.marp.app.elgoog.core.ui.UiState
import dev.bogdanzurac.marp.app.elgoog.core.flowOf
import dev.bogdanzurac.marp.app.elgoog.core.foldResult
import dev.bogdanzurac.marp.app.elgoog.core.onFailure
import dev.bogdanzurac.marp.app.elgoog.core.ui.getGenericErrorDialogFor
import dev.bogdanzurac.marp.app.elgoog.movies.MoviesListViewModel.MoviesListUiState
import dev.bogdanzurac.marp.app.elgoog.movies.MoviesListViewModel.MoviesListUiState.*
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Factory

@Factory
internal class MoviesListViewModel(
    private val dialogManager: DialogManager,
    private val navigator: MoviesNavigator,
    private val moviesRepository: MoviesRepository,
) : BaseViewModel<MoviesListUiState>(), MoviesListUiEvents {

    override val uiState: StateFlow<MoviesListUiState> =
        flowOf { moviesRepository.getTrendingMovies() }
            .onFailure { dialogManager.showDialog(getGenericErrorDialogFor(it)) }
            .foldResult({ Success(it) }, { Error(it) })
            .asState(Loading)

    internal sealed class MoviesListUiState : UiState {
        class Error(val exception: Throwable) : MoviesListUiState()
        class Success(val movies: List<MovieModel>) : MoviesListUiState()
        object Loading : MoviesListUiState()
    }

    override fun navigateToDetails(id: Long) {
        navigator.navigateToMovieDetails(id)
    }
}

internal interface MoviesListUiEvents {
    fun navigateToDetails(id: Long)
}