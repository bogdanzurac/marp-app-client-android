package dev.bogdanzurac.marp.app.elgoog.news

import dev.bogdanzurac.marp.app.elgoog.core.ui.BaseViewModel
import dev.bogdanzurac.marp.app.elgoog.core.arch.DialogManager
import dev.bogdanzurac.marp.app.elgoog.core.ui.UiState
import dev.bogdanzurac.marp.app.elgoog.core.flowOf
import dev.bogdanzurac.marp.app.elgoog.core.foldResult
import dev.bogdanzurac.marp.app.elgoog.core.onFailure
import dev.bogdanzurac.marp.app.elgoog.core.ui.getGenericErrorDialogFor
import dev.bogdanzurac.marp.app.elgoog.news.NewsDetailsViewModel.NewsDetailsUiState
import dev.bogdanzurac.marp.app.elgoog.news.NewsDetailsViewModel.NewsDetailsUiState.*
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Factory

@Factory
internal class NewsDetailsViewModel(
    private val articleId: String,
    private val dialogManager: DialogManager,
    private val newsRepository: NewsRepository,
) : BaseViewModel<NewsDetailsUiState>() {

    override val uiState: StateFlow<NewsDetailsUiState> =
        flowOf { newsRepository.getNewsArticle(articleId) }
            .onFailure { dialogManager.showDialog(getGenericErrorDialogFor(it)) }
            .foldResult({ Success(it) }, { Error(it) })
            .asState(Loading)

    internal sealed class NewsDetailsUiState : UiState {
        class Error(val exception: Throwable) : NewsDetailsUiState()
        class Success(val newsArticleModel: NewsArticleModel) : NewsDetailsUiState()
        object Loading : NewsDetailsUiState()
    }
}