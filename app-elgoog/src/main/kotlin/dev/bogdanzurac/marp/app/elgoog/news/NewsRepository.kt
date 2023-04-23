package dev.bogdanzurac.marp.app.elgoog.news

import dev.bogdanzurac.marp.app.elgoog.core.data.DataHolder
import dev.bogdanzurac.marp.app.elgoog.core.data.SyncTimestampsPreferences
import dev.bogdanzurac.marp.core.mapResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single
import kotlin.time.Duration.Companion.minutes

@Single
class NewsRepository(
    private val dao: NewsDao,
    private val webService: NewsDataWebService,
    syncTimestampsPreferences: SyncTimestampsPreferences,
) {

    private val newsHolder = DataHolder.Builder<List<NewsArticleModel>>("news")
        .fetcher { webService.getNews() }
        .cache(
            reader = {
                dao.observeAll<NewsArticleEntity>()
                    .map { entities -> entities.map { it.toModel() } }
            },
            writer = { models -> dao.saveAll(models.map { it.toEntity() }) },
            cleaner = { dao.deleteAll<NewsArticleEntity>() },
        )
        .timeToLive(10.minutes, syncTimestampsPreferences)
        .build()

    suspend fun getNewsArticles(refresh: Boolean = false): Result<List<NewsArticleModel>> =
        withContext(Dispatchers.IO) {
            newsHolder.get(refresh)
        }

    fun observeNewsArticles(refresh: Boolean = false): Flow<Result<List<NewsArticleModel>>> =
        newsHolder.observe(shouldRefresh = refresh)
            .flowOn(Dispatchers.IO)

    suspend fun getNewsArticle(id: String): Result<NewsArticleModel> =
        withContext(Dispatchers.IO) {
            getNewsArticles()
                .map { newsArticles -> newsArticles.first { it.id == id } }
        }

    fun observeNewsArticle(id: String, refresh: Boolean = false): Flow<Result<NewsArticleModel>> =
        observeNewsArticles(refresh = refresh)
            .mapResult { newsArticles -> newsArticles.first { it.id == id } }
}
