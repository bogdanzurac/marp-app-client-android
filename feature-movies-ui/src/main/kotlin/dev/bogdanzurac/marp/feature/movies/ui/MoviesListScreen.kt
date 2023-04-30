package dev.bogdanzurac.marp.feature.movies.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.bogdanzurac.marp.core.ui.DateTimeAttribute.DAY_MONTH_YEAR
import dev.bogdanzurac.marp.core.ui.composable.BaseScreen
import dev.bogdanzurac.marp.core.ui.composable.EmptyView
import dev.bogdanzurac.marp.core.ui.composable.LoadingView
import dev.bogdanzurac.marp.core.ui.format
import dev.bogdanzurac.marp.core.ui.toLocalDateTime
import dev.bogdanzurac.marp.feature.movies.domain.Movie
import dev.bogdanzurac.marp.feature.movies.ui.MoviesListViewModel.MoviesListUiState.*
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun MoviesListScreen(viewModel: MoviesListViewModel = koinViewModel()) =
    BaseScreen(viewModel) { state ->
        when (val uiState = state.value) {
            is Loading -> LoadingView()
            is Error -> EmptyView()
            is Success ->
                if (uiState.movies.isEmpty()) EmptyView()
                else MoviesListView(uiState.movies, viewModel)
        }
    }

@Composable
private fun MoviesListView(
    movies: List<Movie>,
    events: MoviesListUiEvents,
) {
    LazyColumn {
        items(movies, { it.id }) { movie ->
            MovieView(movie) { events.onMovieClicked(it) }
        }
    }
}

@Composable
private fun MovieView(
    movie: Movie,
    onMovieClicked: (id: Long) -> Unit
) {
    Card(
        Modifier
            .padding(16.dp)
            .clickable { onMovieClicked(movie.id) }) {
        Row(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = movie.posterUrl,
                modifier = Modifier.fillMaxWidth(0.5f),
                contentDescription = movie.title,
                contentScale = ContentScale.FillWidth,
            )
            Column(Modifier.padding(16.dp)) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.headlineSmall
                )
                movie.releaseDate?.let {
                    Text(
                        text = it.toLocalDateTime()
                            .format(LocalContext.current, DAY_MONTH_YEAR),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
                MovieRatingView(movie)
            }
        }
    }
}

@Composable
@Preview
private fun MoviesListPreview() {
    MaterialTheme {
        MoviesListView(
            movies = MutableList(20) { composeMovieModelPreview },
            events = object : MoviesListUiEvents {
                override fun onMovieClicked(id: Long) {}
            })
    }
}