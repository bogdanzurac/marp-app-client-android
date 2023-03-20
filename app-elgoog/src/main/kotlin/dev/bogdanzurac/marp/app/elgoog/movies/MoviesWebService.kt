package dev.bogdanzurac.marp.app.elgoog.movies

import MOVIES_API_KEY
import dev.bogdanzurac.marp.app.elgoog.core.feature.FeatureManager
import dev.bogdanzurac.marp.app.elgoog.core.getLocale
import dev.bogdanzurac.marp.app.elgoog.core.ws.MockNetworkDelayFeature
import dev.bogdanzurac.marp.app.elgoog.core.ws.MockNetworkErrorsFeature
import dev.bogdanzurac.marp.app.elgoog.core.ws.WebService
import io.ktor.client.request.*
import org.koin.core.annotation.Single

/**
 * https://developers.themoviedb.org/3/getting-started/introduction
 */
@Single
class MoviesWebService(featureManager: FeatureManager) :
    WebService(
        "https://api.themoviedb.org",
        featureManager.isEnabled(MockNetworkDelayFeature),
        featureManager.isEnabled(MockNetworkErrorsFeature)
    ) {

    override val requestBuilder: HttpRequestBuilder.() -> Unit = {
        parameter("api_key", MOVIES_API_KEY)
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
