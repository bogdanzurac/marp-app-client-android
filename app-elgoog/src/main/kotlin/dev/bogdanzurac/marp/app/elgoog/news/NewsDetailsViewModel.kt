package dev.bogdanzurac.marp.app.elgoog.news

import dev.bogdanzurac.marp.core.prompts.DialogManager
import dev.bogdanzurac.marp.core.ui.BaseViewModel
import dev.bogdanzurac.marp.core.ui.Tracker
import dev.bogdanzurac.marp.core.ui.UiState
import dev.bogdanzurac.marp.core.ui.getGenericErrorDialogFor
import dev.bogdanzurac.marp.app.elgoog.news.NewsDetailsViewModel.NewsDetailsUiState
import dev.bogdanzurac.marp.app.elgoog.news.NewsDetailsViewModel.NewsDetailsUiState.*
import dev.bogdanzurac.marp.core.flowOf
import dev.bogdanzurac.marp.core.foldResult
import dev.bogdanzurac.marp.core.logger
import dev.bogdanzurac.marp.core.onFailure
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import org.koin.core.annotation.Factory

@Factory
internal class NewsDetailsViewModel(
    private val articleId: String,
    private val dialogManager: DialogManager,
    private val newsRepository: NewsRepository,
    private val tracker: Tracker,
) : BaseViewModel<NewsDetailsUiState>() {

    override val uiState: StateFlow<NewsDetailsUiState> =
        flowOf { newsRepository.getNewsArticle(articleId) }
            .onStart { tracker.trackScreen(NEWS_DETAILS_SCREEN, articleId) }
            .onFailure {
                logger.e("Could not get news details", it)
                dialogManager.showDialog(getGenericErrorDialogFor(it))
            }
            .foldResult({ Success(it) }, { Error(it) })
            .asState(Loading)

    internal sealed class NewsDetailsUiState : UiState {
        class Error(val exception: Throwable) : NewsDetailsUiState()
        class Success(val newsArticleModel: NewsArticleModel) : NewsDetailsUiState()
        object Loading : NewsDetailsUiState()
    }
}