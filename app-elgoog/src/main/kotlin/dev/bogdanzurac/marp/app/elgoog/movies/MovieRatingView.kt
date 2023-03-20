package dev.bogdanzurac.marp.app.elgoog.movies

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.bogdanzurac.marp.app.elgoog.core.theme.ElgoogTheme

@Composable
internal fun MovieRatingView(
    movie: MovieModel,
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
    ElgoogTheme {
        MovieRatingView(composeMovieModelPreview)
    }
}