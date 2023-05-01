package dev.bogdanzurac.marp.feature.news.data.newsapi

import dev.bogdanzurac.marp.core.feature.FeatureManager
import dev.bogdanzurac.marp.core.getLocale
import dev.bogdanzurac.marp.core.ws.MockNetworkDelayFeature
import dev.bogdanzurac.marp.core.ws.MockNetworkErrorsFeature
import dev.bogdanzurac.marp.core.ws.WebService
import dev.bogdanzurac.marp.feature.news.data.NewsWebService
import dev.bogdanzurac.marp.feature.news.domain.NewsArticle
import io.ktor.client.request.*
import org.koin.core.annotation.Property
import org.koin.core.annotation.Single

/**
 * https://newsapi.org/docs
 */
@Single
internal class NewsApiWebService(
    @Property(NEWSAPI_KEY) val apiKey: String,
    featureManager: FeatureManager
) : WebService(
    "https://newsapi.org",
    featureManager.isEnabled(MockNetworkDelayFeature),
    featureManager.isEnabled(MockNetworkErrorsFeature)
), NewsWebService {

    override val requestBuilder: HttpRequestBuilder.() -> Unit = {
        bearerAuth(apiKey)
        parameter("country", getLocale())
    }

    override suspend fun getNews(): Result<List<NewsArticle>> =
        get<NewsModel>("/v2/top-headlines")
            .map { it.list }
            .map { articles -> articles.map { it.toNewsArticle() } }
}

const val NEWSAPI_KEY = "newsapi_api_key"
