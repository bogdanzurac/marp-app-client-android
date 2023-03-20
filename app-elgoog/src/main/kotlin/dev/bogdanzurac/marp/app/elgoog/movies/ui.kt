package dev.bogdanzurac.marp.app.elgoog.movies

import kotlinx.datetime.LocalDate

val composeMovieModelPreview =
    MovieModel(
        123,
        "Scary Movie 13",
        listOf(1, 2, 3),
        listOf(GenreModel(1, "Comedy")),
        LocalDate.parse("2023-03-04"),
        "This is a very nice comedy movie that you can enjoy with friends and family",
        "abc1234",
        4.6,
        120,
        120
    )
