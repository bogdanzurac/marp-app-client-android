package dev.bogdanzurac.marp.app.elgoog.news

import dev.bogdanzurac.marp.app.elgoog.core.navigation.AppRoute

sealed class NewsRoute(override val path: String) : AppRoute {
    object Root : NewsRoute("news")
    object NewsList : NewsRoute("news/list")
    class NewsDetails(id: String? = null) :
        NewsRoute("news/list/" + (id ?: "{$NEWS_ID_ARG}")) {
        companion object {
            const val NEWS_ID_ARG = "id"
        }
    }
}
