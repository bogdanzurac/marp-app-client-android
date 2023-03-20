@file:OptIn(ExperimentalMaterialApi::class)

package dev.bogdanzurac.marp.app.elgoog.news

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.bogdanzurac.marp.app.elgoog.core.theme.ElgoogTheme
import dev.bogdanzurac.marp.app.elgoog.core.ui.*
import dev.bogdanzurac.marp.app.elgoog.core.ui.DateTimeAttribute.DATE_TIME_SHORT
import dev.bogdanzurac.marp.app.elgoog.core.ui.composable.BaseScreen
import dev.bogdanzurac.marp.app.elgoog.core.ui.composable.EmptyView
import dev.bogdanzurac.marp.app.elgoog.core.ui.composable.LoadingView
import dev.bogdanzurac.marp.app.elgoog.news.NewsListViewModel.NewsListUiState.*
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun NewsListScreen(viewModel: NewsListViewModel = koinViewModel()) =
    BaseScreen(viewModel) { state ->
        when (val uiState = state.value) {
            is Loading -> LoadingView()
            is Error -> EmptyView()
            is Success -> NewsListRefreshView(uiState, viewModel)
        }
    }

@Composable
private fun NewsListRefreshView(
    successState: Success,
    uiEvents: NewsListUiEvents,
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = successState.isLoading,
        onRefresh = { uiEvents.refreshList() }
    )
    Box(Modifier.pullRefresh(pullRefreshState)) {
        if (successState.newsArticles.isEmpty()) EmptyView()
        else NewsListView(successState.newsArticles, uiEvents)
        PullRefreshIndicator(
            successState.isLoading,
            pullRefreshState,
            Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
private fun NewsListView(
    newsArticles: List<NewsArticleModel>,
    uiEvents: NewsListUiEvents,
) {
    LazyColumn {
        items(newsArticles, { it.id }) { article ->
            NewsListRowView(article) { uiEvents.navigateToDetails(it) }
        }
    }
}

@Composable
private fun NewsListRowView(
    newsArticle: NewsArticleModel,
    onArticleClicked: (id: String) -> Unit
) {
    Card(
        Modifier
            .padding(16.dp)
            .clickable { onArticleClicked(newsArticle.id) }) {
        AsyncImage(
            model = newsArticle.imageUrl,
            modifier = Modifier.fillMaxWidth(),
            contentDescription = newsArticle.title,
            contentScale = ContentScale.FillWidth,
        )
        Column(Modifier.padding(16.dp)) {
            Text(text = newsArticle.title, style = MaterialTheme.typography.headlineSmall)
            Text(
                text = newsArticle.publishDate
                    .toLocalDateTime()
                    .format(LocalContext.current, DATE_TIME_SHORT),
                style = MaterialTheme.typography.labelMedium
            )
            newsArticle.description?.let {
                Text(
                    text = it,
                    modifier = Modifier.padding(top = 16.dp),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }
}

@Composable
@Preview
private fun NewsListPreview() {
    ElgoogTheme {
        NewsListView(
            newsArticles = MutableList(20) { composeNewsArticleModelPreview },
            uiEvents = object : NewsListUiEvents {
                override fun navigateToDetails(id: String) {}
                override fun refreshList() {}
            }
        )
    }
}