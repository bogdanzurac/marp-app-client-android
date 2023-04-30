package dev.bogdanzurac.marp.feature.movies.domain

import kotlinx.datetime.LocalDate

data class Movie(
    val id: Long,
    val title: String,
    val genres: List<Genre> = emptyList(),
    val releaseDate: LocalDate? = null,
    val description: String,
    val rating: Double,
    val ratingCount: Long,
    val runtime: Long? = null,
    val posterUrl: String
)

data class Genre(
    val id: Long,
    val name: String,
)