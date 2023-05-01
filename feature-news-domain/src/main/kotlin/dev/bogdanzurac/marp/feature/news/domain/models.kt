package dev.bogdanzurac.marp.feature.news.domain

import kotlinx.datetime.Instant

data class NewsArticle(
    val id: String,
    val imageUrl: String?,
    val author: List<String>?,
    val title: String,
    val link: String,
    val description: String?,
    val content: String?,
    val publishDate: Instant,
)
