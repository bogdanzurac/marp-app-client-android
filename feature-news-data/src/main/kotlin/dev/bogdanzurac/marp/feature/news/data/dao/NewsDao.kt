package dev.bogdanzurac.marp.feature.news.data.dao

import dev.bogdanzurac.marp.core.db.BaseDao
import org.koin.core.annotation.Single

@Single
internal class NewsDao : BaseDao<NewsArticleEntity>()