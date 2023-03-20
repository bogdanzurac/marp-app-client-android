package dev.bogdanzurac.marp.app.elgoog.news

import dev.bogdanzurac.marp.app.elgoog.core.db.AppDatabase
import dev.bogdanzurac.marp.app.elgoog.core.db.BaseDao
import org.koin.core.annotation.Single

@Single
class NewsDao(appDatabase: AppDatabase) : BaseDao<NewsArticleEntity>(appDatabase)