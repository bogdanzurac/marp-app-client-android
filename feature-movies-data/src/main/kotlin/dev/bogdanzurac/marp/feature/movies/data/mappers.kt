package dev.bogdanzurac.marp.feature.movies.data

import dev.bogdanzurac.marp.feature.movies.domain.Genre
import dev.bogdanzurac.marp.feature.movies.domain.Movie

fun List<MovieModel>.toMovies() = map { it.toMovie() }

fun MovieModel.toMovie() = Movie(
    id,
    title,
    genres.map { it.toGenre() },
    releaseDate,
    description,
    rating,
    ratingCount,
    runtime,
    posterUrl
)

fun GenreModel.toGenre() = Genre(id, name)