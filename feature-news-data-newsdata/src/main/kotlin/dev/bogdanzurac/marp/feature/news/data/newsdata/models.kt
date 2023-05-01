package dev.bogdanzurac.marp.feature.news.data.newsdata

import dev.bogdanzurac.marp.core.ws.CustomInstantSerializer
import dev.bogdanzurac.marp.core.*
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class NewsModel(
    @SerialName("results") val list: List<NewsArticleModel>
)

@Serializable
internal data class NewsArticleModel(
    @SerialName("id")
    val id: String = randomUUID(),
    @SerialName("image_url")
    val imageUrl: String?,
    @SerialName("creator")
    val author: List<String>?,
    @SerialName("title")
    val title: String,
    @SerialName("link")
    val link: String,
    @SerialName("description")
    val description: String?,
    @SerialName("content")
    val content: String,
    @SerialName("pubDate")
    @Serializable(with = CustomInstantSerializer::class)
    val publishDate: Instant,
)
