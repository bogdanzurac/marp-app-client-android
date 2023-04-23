package dev.bogdanzurac.marp.app.elgoog.news

import NEWS_API_KEY
import dev.bogdanzurac.marp.core.feature.FeatureManager
import dev.bogdanzurac.marp.app.elgoog.core.getLocale
import dev.bogdanzurac.marp.app.elgoog.core.ws.MockNetworkDelayFeature
import dev.bogdanzurac.marp.app.elgoog.core.ws.MockNetworkErrorsFeature
import dev.bogdanzurac.marp.app.elgoog.core.ws.WebService
import io.ktor.client.request.*
import org.koin.core.annotation.Single

/**
 * https://newsdata.io/documentation
 */
@Single
class NewsDataWebService(featureManager: FeatureManager) : WebService(
    "https://newsdata.io",
    featureManager.isEnabled(MockNetworkDelayFeature),
    featureManager.isEnabled(MockNetworkErrorsFeature)
) {

    override val requestBuilder: HttpRequestBuilder.() -> Unit = {
        parameter("apikey", NEWS_API_KEY)
        parameter("language", getLocale())
    }

    suspend fun getNews(): Result<List<NewsArticleModel>> =
        get<NewsModel>("/api/1/news").map { it.list }
}
