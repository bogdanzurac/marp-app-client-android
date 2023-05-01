package dev.bogdanzurac.marp.feature.news.data

import dev.bogdanzurac.marp.feature.news.domain.NewsArticle

interface NewsWebService {

    suspend fun getNews(): Result<List<NewsArticle>>
}