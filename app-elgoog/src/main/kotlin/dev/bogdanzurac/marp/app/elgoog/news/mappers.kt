package dev.bogdanzurac.marp.app.elgoog.news

import kotlinx.datetime.Instant

fun NewsArticleModel.toEntity(): NewsArticleEntity =
    NewsArticleEntity().also {
        it.id = id
        it.imageUrl = imageUrl
        it.creator = author
        it.title = title
        it.link = link
        it.description = description
        it.content = content
        it.publishDate = publishDate.toEpochMilliseconds()
    }

fun NewsArticleEntity.toModel(): NewsArticleModel =
    NewsArticleModel(
        id, imageUrl, creator, title, link, description, content,
        Instant.fromEpochMilliseconds(publishDate)
    )
