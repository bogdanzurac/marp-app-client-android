package dev.bogdanzurac.marp.app.elgoog.news

import dev.bogdanzurac.marp.core.db.BaseDao
import org.koin.core.annotation.Single

@Single
class NewsDao : BaseDao<NewsArticleEntity>()