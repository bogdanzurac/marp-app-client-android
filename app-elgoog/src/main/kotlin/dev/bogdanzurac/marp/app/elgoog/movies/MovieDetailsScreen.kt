package dev.bogdanzurac.marp.app.elgoog.movies

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.bogdanzurac.marp.app.elgoog.R
import dev.bogdanzurac.marp.app.elgoog.core.theme.ElgoogTheme
import dev.bogdanzurac.marp.app.elgoog.core.ui.DateTimeAttribute
import dev.bogdanzurac.marp.app.elgoog.core.ui.composable.BaseScreen
import dev.bogdanzurac.marp.app.elgoog.core.ui.composable.EmptyView
import dev.bogdanzurac.marp.app.elgoog.core.ui.composable.LoadingView
import dev.bogdanzurac.marp.app.elgoog.core.ui.composable.SelectableText
import dev.bogdanzurac.marp.app.elgoog.core.ui.format
import dev.bogdanzurac.marp.app.elgoog.core.ui.toLocalDateTime
import dev.bogdanzurac.marp.app.elgoog.movies.MovieDetailsViewModel.MovieDetailsUiState.*
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
internal fun MovieDetailsScreen(
    movieId: Long,
    viewModel: MovieDetailsViewModel = koinViewModel(parameters = { parametersOf(movieId) })
) = BaseScreen(viewModel) { state ->
    when (val uiState = state.value) {
        is Loading -> LoadingView()
        is Error -> EmptyView()
        is Success -> MovieDetailsView(uiState.movie)
    }
}

@Composable
private fun MovieDetailsView(movie: MovieModel) {
    Column(Modifier.verticalScroll(rememberScrollState())) {
        AsyncImage(
            model = movie.posterUrl,
            modifier = Modifier.fillMaxWidth(),
            contentDescription = movie.title,
            contentScale = ContentScale.FillWidth,
        )
        Column(Modifier.padding(16.dp)) {
            SelectableText(
                text = movie.title,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = movie.releaseDate.toLocalDateTime()
                    .format(LocalContext.current, DateTimeAttribute.DAY_MONTH_YEAR),
                style = MaterialTheme.typography.labelMedium
            )
            MovieGenresView(movie)
            SelectableText(
                text = movie.description,
                modifier = Modifier.padding(top = 16.dp),
                style = MaterialTheme.typography.bodyLarge,
            )
            movie.runtime?.let {
                Text(
                    text = stringResource(R.string.movie_runtime, it),
                    modifier = Modifier.padding(top = 16.dp),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            MovieRatingView(movie)
        }
    }
}

@Composable
private fun MovieGenresView(
    movie: MovieModel,
) {
    if (movie.genres.isEmpty()) return
    Row(modifier = Modifier.padding(top = 8.dp)) {
        movie.genres.forEach {
            Text(
                text = it.name,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(4.dp),
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}

@Composable
@Preview
private fun MovieDetailsPreview() {
    ElgoogTheme {
        MovieDetailsView(composeMovieModelPreview)
    }
}