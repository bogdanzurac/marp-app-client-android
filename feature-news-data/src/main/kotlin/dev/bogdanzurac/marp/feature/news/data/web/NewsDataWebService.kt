package dev.bogdanzurac.marp.feature.news.data.web

import dev.bogdanzurac.marp.core.feature.FeatureManager
import dev.bogdanzurac.marp.core.getLocale
import dev.bogdanzurac.marp.core.ws.MockNetworkDelayFeature
import dev.bogdanzurac.marp.core.ws.MockNetworkErrorsFeature
import dev.bogdanzurac.marp.core.ws.WebService
import io.ktor.client.request.*
import org.koin.core.annotation.Property
import org.koin.core.annotation.Single

/**
 * https://newsdata.io/documentation
 */
@Single
internal class NewsDataWebService(
    @Property(NEWS_KEY) val apiKey: String,
    featureManager: FeatureManager
) : WebService(
    "https://newsdata.io",
    featureManager.isEnabled(MockNetworkDelayFeature),
    featureManager.isEnabled(MockNetworkErrorsFeature)
) {

    override val requestBuilder: HttpRequestBuilder.() -> Unit = {
        parameter("apikey", apiKey)
        parameter("language", getLocale())
    }

    suspend fun getNews(): Result<List<NewsArticleModel>> =
        get<NewsModel>("/api/1/news").map { it.list }
}

const val NEWS_KEY = "news_api_key"
