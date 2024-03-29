@file:OptIn(ExperimentalMaterialApi::class)

package dev.bogdanzurac.marp.feature.news.ui

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
import dev.bogdanzurac.marp.core.ui.*
import dev.bogdanzurac.marp.core.ui.DateTimeAttribute.DATE_TIME_SHORT
import dev.bogdanzurac.marp.core.ui.composable.BaseScreen
import dev.bogdanzurac.marp.core.ui.composable.EmptyView
import dev.bogdanzurac.marp.core.ui.composable.LoadingView
import dev.bogdanzurac.marp.feature.news.domain.NewsArticle
import dev.bogdanzurac.marp.feature.news.ui.NewsListViewModel.NewsListUiState.*
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
    state: Success,
    events: NewsListUiEvents,
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isLoading,
        onRefresh = { events.onListRefreshRequested() }
    )
    Box(Modifier.pullRefresh(pullRefreshState)) {
        if (state.newsArticles.isEmpty()) EmptyView()
        else NewsListView(state.newsArticles, events)
        PullRefreshIndicator(
            state.isLoading,
            pullRefreshState,
            Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
private fun NewsListView(
    newsArticles: List<NewsArticle>,
    events: NewsListUiEvents,
) {
    LazyColumn {
        items(newsArticles, { it.id }) { article ->
            NewsListRowView(article) { events.onArticleClicked(it) }
        }
    }
}

@Composable
private fun NewsListRowView(
    newsArticle: NewsArticle,
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
    MaterialTheme {
        NewsListView(
            newsArticles = MutableList(20) { composeNewsArticleModelPreview },
            events = object : NewsListUiEvents {
                override fun onArticleClicked(id: String) {}
                override fun onListRefreshRequested() {}
            }
        )
    }
}