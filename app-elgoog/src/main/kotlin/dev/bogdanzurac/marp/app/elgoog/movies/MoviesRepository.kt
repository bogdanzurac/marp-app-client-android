package dev.bogdanzurac.marp.app.elgoog.movies

import dev.bogdanzurac.marp.app.elgoog.core.data.DataHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single
import kotlin.time.Duration.Companion.minutes

@Single
class MoviesRepository(
    private val webService: MoviesWebService,
) {

    private val trendingMoviesHolder = DataHolder.Builder<List<MovieModel>>("movies")
        .fetcher { webService.getTrendingMovies() }
        .timeToLive(10.minutes)
        .build()

    suspend fun getTrendingMovies(refresh: Boolean = false): Result<List<MovieModel>> =
        withContext(Dispatchers.IO) {
            trendingMoviesHolder.get(refresh)
        }

    suspend fun searchMovies(query: String): Result<List<MovieModel>> =
        withContext(Dispatchers.IO) {
            webService.searchMovies(query)
        }

    suspend fun getMovie(id: Long): Result<MovieModel> =
        withContext(Dispatchers.IO) {
            webService.getMovie(id)
        }
}
