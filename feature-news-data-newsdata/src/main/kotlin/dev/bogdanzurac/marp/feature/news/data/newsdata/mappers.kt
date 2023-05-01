package dev.bogdanzurac.marp.feature.news.data.newsdata

import dev.bogdanzurac.marp.feature.news.domain.NewsArticle

internal fun NewsArticleModel.toNewsArticle(): NewsArticle =
    NewsArticle(id, imageUrl, author, title, link, description, content, publishDate)
