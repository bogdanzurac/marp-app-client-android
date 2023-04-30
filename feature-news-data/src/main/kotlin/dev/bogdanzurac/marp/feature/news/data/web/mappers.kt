package dev.bogdanzurac.marp.feature.news.data.web

import dev.bogdanzurac.marp.feature.news.domain.NewsArticle

internal fun NewsArticleModel.toNewsArticle(): NewsArticle =
    NewsArticle(id, imageUrl, author, title, link, description, content, publishDate)

internal fun NewsArticle.toModel(): NewsArticleModel =
    NewsArticleModel(id, imageUrl, author, title, link, description, content, publishDate)
