package dev.bogdanzurac.marp.feature.movies.domain

interface MoviesRepository {

    suspend fun getTrendingMovies(refresh: Boolean = false): Result<List<Movie>>

    suspend fun searchMovies(query: String): Result<List<Movie>>

    suspend fun getMovie(id: Long): Result<Movie>
}