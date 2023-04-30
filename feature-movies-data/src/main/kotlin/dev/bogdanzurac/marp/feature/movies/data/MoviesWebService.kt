package dev.bogdanzurac.marp.feature.movies.data

import dev.bogdanzurac.marp.core.feature.FeatureManager
import dev.bogdanzurac.marp.core.getLocale
import dev.bogdanzurac.marp.core.ws.MockNetworkDelayFeature
import dev.bogdanzurac.marp.core.ws.MockNetworkErrorsFeature
import dev.bogdanzurac.marp.core.ws.WebService
import io.ktor.client.request.*
import org.koin.core.annotation.Property
import org.koin.core.annotation.Single

/**
 * https://developers.themoviedb.org/3/getting-started/introduction
 */
@Single
internal class MoviesWebService(
    @Property(MOVIES_KEY) val apiKey: String,
    featureManager: FeatureManager
) : WebService(
    "https://api.themoviedb.org",
    featureManager.isEnabled(MockNetworkDelayFeature),
    featureManager.isEnabled(MockNetworkErrorsFeature)
) {

    override val requestBuilder: HttpRequestBuilder.() -> Unit = {
        parameter("api_key", apiKey)
        parameter("language", getLocale())
    }

    suspend fun getTrendingMovies(): Result<List<MovieModel>> =
        get<MoviesModel>("/3/movie/popular")
            .map { it.list }

    suspend fun searchMovies(query: String): Result<List<MovieModel>> =
        get<MoviesModel>("/3/search/movie") { parameter("query", query) }
            .map { it.list }

    suspend fun getMovie(id: Long): Result<MovieModel> =
        get("/3/movie/$id")
}

const val MOVIES_KEY = "movies_api_key"