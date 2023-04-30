package dev.bogdanzurac.marp.feature.movies.ui

import dev.bogdanzurac.marp.feature.movies.domain.Genre
import dev.bogdanzurac.marp.feature.movies.domain.Movie
import kotlinx.datetime.LocalDate

val composeMovieModelPreview =
    Movie(
        123,
        "Scary Movie 13",
        listOf(Genre(1, "Comedy")),
        LocalDate.parse("2023-03-04"),
        "This is a very nice comedy movie that you can enjoy with friends and family",
        4.6,
        120,
        120,
        "abc"
    )
