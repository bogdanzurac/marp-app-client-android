package dev.bogdanzurac.marp.app.elgoog.movies

import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesModel(
    @SerialName("results") val list: List<MovieModel>
)

@Serializable
data class MovieModel(
    @SerialName("id")
    val id: Long,
    @SerialName("title")
    val title: String,
    @SerialName("genre_ids")
    val genreIds: List<Long> = emptyList(),
    @SerialName("genres")
    val genres: List<GenreModel> = emptyList(),
    @SerialName("release_date")
    val releaseDate: LocalDate,
    @SerialName("overview")
    val description: String,
    @SerialName("poster_path")
    private val posterPath: String,
    @SerialName("vote_average")
    val rating: Double,
    @SerialName("vote_count")
    val ratingCount: Long,
    @SerialName("runtime")
    val runtime: Long? = null,
) {
    val posterUrl: String = "https://image.tmdb.org/t/p/original$posterPath"
}

@Serializable
data class GenreModel(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
)