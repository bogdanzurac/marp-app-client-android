package dev.bogdanzurac.marp.app.elgoog.news

import dev.bogdanzurac.marp.core.ws.CustomInstantSerializer
import dev.bogdanzurac.marp.core.*
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Ignore
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsModel(
    @SerialName("results") val list: List<NewsArticleModel>
)

@Serializable
data class NewsArticleModel(
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

class NewsArticleEntity : RealmObject {
    @PrimaryKey
    var id: String = ""
    var imageUrl: String? = null

    @Ignore
    var creator: List<String>? = null
    var title: String = ""
    var link: String = ""
    var description: String? = null
    var content: String = ""
    var publishDate: Long = 0
}