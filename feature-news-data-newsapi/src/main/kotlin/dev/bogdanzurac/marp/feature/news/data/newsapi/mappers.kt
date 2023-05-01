package dev.bogdanzurac.marp.feature.news.data.newsapi

import dev.bogdanzurac.marp.feature.news.domain.NewsArticle

internal fun NewsArticleModel.toNewsArticle(): NewsArticle =
    NewsArticle(
        id,
        imageUrl,
        author?.let { listOf(it) },
        title,
        link,
        description,
        content,
        publishDate
    )
