package dev.bogdanzurac.marp.app.elgoog.news

import androidx.lifecycle.viewModelScope
import dev.bogdanzurac.marp.app.elgoog.core.arch.DialogManager
import dev.bogdanzurac.marp.app.elgoog.core.logger
import dev.bogdanzurac.marp.app.elgoog.core.onFailure
import dev.bogdanzurac.marp.app.elgoog.core.ui.BaseViewModel
import dev.bogdanzurac.marp.app.elgoog.core.ui.Tracker
import dev.bogdanzurac.marp.app.elgoog.core.ui.UiState
import dev.bogdanzurac.marp.app.elgoog.core.ui.getGenericErrorDialogFor
import dev.bogdanzurac.marp.app.elgoog.news.NewsListViewModel.NewsListUiState
import dev.bogdanzurac.marp.app.elgoog.news.NewsListViewModel.NewsListUiState.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory

@Factory
internal class NewsListViewModel(
    private val dialogManager: DialogManager,
    private val newsNavigator: NewsNavigator,
    private val newsRepository: NewsRepository,
    private val tracker: Tracker,
) : BaseViewModel<NewsListUiState>(), NewsListUiEvents {

    private val loadingState: MutableStateFlow<Boolean> = MutableStateFlow(false)

    override val uiState: StateFlow<NewsListUiState> =
        newsRepository.observeNewsArticles()
            .onStart { tracker.trackScreen(NEWS_LIST_SCREEN) }
            .onEach { loadingState.tryEmit(false) }
            .onFailure {
                logger.e("Could not get news list", it)
                dialogManager.showDialog(getGenericErrorDialogFor(it))
            }
            .combine(loadingState) { newsArticlesResult, isLoading ->
                newsArticlesResult.fold({ Success(it, isLoading) }, { Error(it) })
            }
            .asState(Loading)

    internal sealed class NewsListUiState : UiState {
        class Error(val exception: Throwable) : NewsListUiState()
        class Success(
            val newsArticles: List<NewsArticleModel>,
            val isLoading: Boolean = false
        ) : NewsListUiState()

        object Loading : NewsListUiState()
    }

    override fun onArticleClicked(id: String) {
        newsNavigator.navigateToNewsDetails(id)
    }

    override fun onListRefreshRequested() {
        viewModelScope.launch {
            loadingState.tryEmit(true)
            newsRepository.getNewsArticles(true)
        }
    }
}

internal interface NewsListUiEvents {
    fun onArticleClicked(id: String)
    fun onListRefreshRequested()
}