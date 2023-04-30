package dev.bogdanzurac.marp.feature.movies.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.bogdanzurac.marp.feature.movies.domain.Movie

@Composable
internal fun MovieRatingView(
    movie: Movie,
) {
    val ratingText = "${movie.rating} / 10 (${movie.ratingCount})"
    Text(
        text = ratingText,
        style = MaterialTheme.typography.bodyMedium,
    )
}

@Composable
@Preview
private fun MovieRatingPreview() {
    MaterialTheme {
        MovieRatingView(composeMovieModelPreview)
    }
}