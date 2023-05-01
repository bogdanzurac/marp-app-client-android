package dev.bogdanzurac.marp.feature.news.data.dao

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Ignore
import io.realm.kotlin.types.annotations.PrimaryKey

class NewsArticleEntity : RealmObject {
    @PrimaryKey
    var id: String = ""
    var imageUrl: String? = null

    @Ignore
    var creator: List<String>? = null
    var title: String = ""
    var link: String = ""
    var description: String? = null
    var content: String? = null
    var publishDate: Long = 0
}