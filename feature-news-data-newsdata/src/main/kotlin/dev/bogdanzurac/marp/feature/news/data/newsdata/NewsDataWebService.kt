package dev.bogdanzurac.marp.feature.news.data.newsdata

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
 * https://newsdata.io/documentation
 */
@Single
internal class NewsDataWebService(
    @Property(NEWSDATA_KEY) val apiKey: String,
    featureManager: FeatureManager
) : WebService(
    "https://newsdata.io",
    featureManager.isEnabled(MockNetworkDelayFeature),
    featureManager.isEnabled(MockNetworkErrorsFeature)
), NewsWebService {

    override val requestBuilder: HttpRequestBuilder.() -> Unit = {
        parameter("apikey", apiKey)
        parameter("language", getLocale())
    }

    override suspend fun getNews(): Result<List<NewsArticle>> =
        get<NewsModel>("/api/1/news")
            .map { it.list }
            .map { articles -> articles.map { it.toNewsArticle() } }
}

const val NEWSDATA_KEY = "newsdata_api_key"
