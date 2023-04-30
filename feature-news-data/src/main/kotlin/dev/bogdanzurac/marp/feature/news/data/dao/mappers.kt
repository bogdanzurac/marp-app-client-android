package dev.bogdanzurac.marp.feature.news.data.dao

import dev.bogdanzurac.marp.feature.news.domain.NewsArticle
import kotlinx.datetime.Instant

internal fun NewsArticle.toEntity(): NewsArticleEntity =
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

internal fun NewsArticleEntity.toNewsArticle(): NewsArticle =
    NewsArticle(
        id, imageUrl, creator, title, link, description, content,
        Instant.fromEpochMilliseconds(publishDate)
    )
