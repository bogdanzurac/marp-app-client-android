package dev.bogdanzurac.marp.feature.news.data

import dev.bogdanzurac.marp.core.data.DataHolder
import dev.bogdanzurac.marp.core.data.SyncTimestampsPreferences
import dev.bogdanzurac.marp.core.mapResult
import dev.bogdanzurac.marp.feature.news.data.dao.NewsArticleEntity
import dev.bogdanzurac.marp.feature.news.data.dao.NewsDao
import dev.bogdanzurac.marp.feature.news.data.dao.toEntity
import dev.bogdanzurac.marp.feature.news.data.dao.toNewsArticle
import dev.bogdanzurac.marp.feature.news.domain.NewsArticle
import dev.bogdanzurac.marp.feature.news.domain.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single
import kotlin.time.Duration.Companion.minutes

@Single
internal class NewsRepositoryImpl(
    private val dao: NewsDao,
    private val webService: NewsWebService,
    syncTimestampsPreferences: SyncTimestampsPreferences,
) : NewsRepository {

    private val newsHolder = DataHolder.Builder<List<NewsArticle>>("news")
        .fetcher { webService.getNews() }
        .cache(
            reader = {
                dao.observeAll<NewsArticleEntity>()
                    .map { entities -> entities.map { it.toNewsArticle() } }
            },
            writer = { models -> dao.saveAll(models.map { it.toEntity() }) },
            cleaner = { dao.deleteAll<NewsArticleEntity>() },
        )
        .timeToLive(10.minutes, syncTimestampsPreferences)
        .build()

    override suspend fun getNewsArticles(refresh: Boolean): Result<List<NewsArticle>> =
        withContext(Dispatchers.IO) {
            newsHolder.get(refresh)
        }

    override fun observeNewsArticles(refresh: Boolean): Flow<Result<List<NewsArticle>>> =
        newsHolder.observe(shouldRefresh = refresh)
            .flowOn(Dispatchers.IO)

    override suspend fun getNewsArticle(id: String): Result<NewsArticle> =
        withContext(Dispatchers.IO) {
            getNewsArticles()
                .map { newsArticles -> newsArticles.first { it.id == id } }
        }

    override fun observeNewsArticle(id: String, refresh: Boolean): Flow<Result<NewsArticle>> =
        observeNewsArticles(refresh = refresh)
            .mapResult { newsArticles -> newsArticles.first { it.id == id } }
}
