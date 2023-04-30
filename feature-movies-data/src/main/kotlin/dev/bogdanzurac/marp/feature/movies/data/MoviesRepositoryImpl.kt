package dev.bogdanzurac.marp.feature.movies.data

import dev.bogdanzurac.marp.core.data.DataHolder
import dev.bogdanzurac.marp.feature.movies.domain.Movie
import dev.bogdanzurac.marp.feature.movies.domain.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single
import kotlin.time.Duration.Companion.minutes

@Single
internal class MoviesRepositoryImpl(
    private val webService: MoviesWebService,
) : MoviesRepository {

    private val trendingMoviesHolder = DataHolder.Builder<List<Movie>>("movies")
        .fetcher { webService.getTrendingMovies().map { it.toMovies() } }
        .timeToLive(10.minutes)
        .build()

    override suspend fun getTrendingMovies(refresh: Boolean): Result<List<Movie>> =
        withContext(Dispatchers.IO) {
            trendingMoviesHolder.get(refresh)
        }

    override suspend fun searchMovies(query: String): Result<List<Movie>> =
        withContext(Dispatchers.IO) {
            webService.searchMovies(query).map { it.toMovies() }
        }

    override suspend fun getMovie(id: Long): Result<Movie> =
        withContext(Dispatchers.IO) {
            webService.getMovie(id).map { it.toMovie() }
        }
}
