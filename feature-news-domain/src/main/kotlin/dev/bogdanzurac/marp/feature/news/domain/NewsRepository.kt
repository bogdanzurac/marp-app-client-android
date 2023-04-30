package dev.bogdanzurac.marp.feature.news.domain

import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    suspend fun getNewsArticles(refresh: Boolean = false): Result<List<NewsArticle>>

    fun observeNewsArticles(refresh: Boolean = false): Flow<Result<List<NewsArticle>>>

    suspend fun getNewsArticle(id: String): Result<NewsArticle>

    fun observeNewsArticle(id: String, refresh: Boolean = false): Flow<Result<NewsArticle>>
}