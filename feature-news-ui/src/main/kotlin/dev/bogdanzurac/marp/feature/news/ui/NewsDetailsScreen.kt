package dev.bogdanzurac.marp.feature.news.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.bogdanzurac.marp.core.ui.DateTimeAttribute.DATE_TIME_SHORT
import dev.bogdanzurac.marp.core.ui.composable.BaseScreen
import dev.bogdanzurac.marp.core.ui.composable.EmptyView
import dev.bogdanzurac.marp.core.ui.composable.LoadingView
import dev.bogdanzurac.marp.core.ui.composable.SelectableText
import dev.bogdanzurac.marp.core.ui.format
import dev.bogdanzurac.marp.core.ui.toLocalDateTime
import dev.bogdanzurac.marp.feature.news.domain.NewsArticle
import dev.bogdanzurac.marp.feature.news.ui.NewsDetailsViewModel.NewsDetailsUiState.*
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
internal fun NewsDetailsScreen(
    articleId: String,
    viewModel: NewsDetailsViewModel = koinViewModel(parameters = { parametersOf(articleId) })
) = BaseScreen(viewModel) { state ->
    when (val uiState = state.value) {
        is Loading -> LoadingView()
        is Error -> EmptyView()
        is Success -> NewsDetailsView(uiState.newsArticle)
    }
}

@Composable
private fun NewsDetailsView(newsArticle: NewsArticle) {
    Column(Modifier.verticalScroll(rememberScrollState())) {
        AsyncImage(
            model = newsArticle.imageUrl,
            modifier = Modifier.fillMaxWidth(),
            contentDescription = newsArticle.title,
            contentScale = ContentScale.FillWidth,
        )
        Column(Modifier.padding(16.dp)) {
            SelectableText(
                text = newsArticle.title,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = newsArticle.publishDate
                    .toLocalDateTime()
                    .format(LocalContext.current, DATE_TIME_SHORT),
                style = MaterialTheme.typography.labelSmall
            )
            newsArticle.author?.let {
                Text(
                    text = it.joinToString(", "),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            newsArticle.description?.let {
                SelectableText(
                    text = it,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            newsArticle.content?.let {
                SelectableText(
                    text = it,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.padding(top = 16.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
@Preview
private fun NewsDetailsPreview() {
    MaterialTheme {
        NewsDetailsView(composeNewsArticleModelPreview)
    }
}
