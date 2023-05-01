package dev.bogdanzurac.marp.feature.news.data.newsapi

import dev.bogdanzurac.marp.core.*
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class NewsModel(
    @SerialName("articles") val list: List<NewsArticleModel>
)

@Serializable
internal data class NewsArticleModel(
    val id: String = randomUUID(),
    @SerialName("urlToImage")
    val imageUrl: String?,
    @SerialName("author")
    val author: String?,
    @SerialName("title")
    val title: String,
    @SerialName("url")
    val link: String,
    @SerialName("description")
    val description: String?,
    @SerialName("content")
    val content: String?,
    @SerialName("publishedAt")
    val publishDate: Instant,
)
