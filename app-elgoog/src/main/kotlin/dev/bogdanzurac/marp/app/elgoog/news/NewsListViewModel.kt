package dev.bogdanzurac.marp.app.elgoog.news

import androidx.lifecycle.viewModelScope
import dev.bogdanzurac.marp.app.elgoog.core.ui.BaseViewModel
import dev.bogdanzurac.marp.app.elgoog.core.arch.DialogManager
import dev.bogdanzurac.marp.app.elgoog.core.ui.UiState
import dev.bogdanzurac.marp.app.elgoog.core.onFailure
import dev.bogdanzurac.marp.app.elgoog.core.ui.getGenericErrorDialogFor
import dev.bogdanzurac.marp.app.elgoog.news.NewsListViewModel.NewsListUiState
import dev.bogdanzurac.marp.app.elgoog.news.NewsListViewModel.NewsListUiState.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory

@Factory
internal class NewsListViewModel(
    private val dialogManager: DialogManager,
    private val newsNavigator: NewsNavigator,
    private val newsRepository: NewsRepository,
) : BaseViewModel<NewsListUiState>(), NewsListUiEvents {

    private val loadingState: MutableStateFlow<Boolean> = MutableStateFlow(false)

    override val uiState: StateFlow<NewsListUiState> =
        newsRepository.observeNewsArticles()
            .onEach { loadingState.tryEmit(false) }
            .onFailure { dialogManager.showDialog(getGenericErrorDialogFor(it)) }
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

    override fun navigateToDetails(id: String) {
        newsNavigator.navigateToNewsDetails(id)
    }

    override fun refreshList() {
        viewModelScope.launch {
            loadingState.tryEmit(true)
            newsRepository.getNewsArticles(true)
        }
    }
}

internal interface NewsListUiEvents {
    fun navigateToDetails(id: String)
    fun refreshList()
}