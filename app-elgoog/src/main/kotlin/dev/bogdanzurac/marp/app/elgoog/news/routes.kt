package dev.bogdanzurac.marp.app.elgoog.news

import dev.bogdanzurac.marp.app.elgoog.core.navigation.AppRoute
import dev.bogdanzurac.marp.app.elgoog.core.navigation.AppRoute.SimpleRoute
import dev.bogdanzurac.marp.app.elgoog.core.navigation.NavArg
import dev.bogdanzurac.marp.app.elgoog.core.navigation.stringArg
import dev.bogdanzurac.marp.app.elgoog.core.navigation.withArg

object News : SimpleRoute("news")

object NewsList : SimpleRoute("news/list")

object NewsDetails : AppRoute.ArgsRoute() {

    internal const val NEWS_ID = "news_id"
    override val args: List<NavArg> = listOf(stringArg(NEWS_ID))
    override val path = "news/list/{${NEWS_ID}}"

    operator fun invoke(id: String? = null): AppRoute =
        SimpleRoute(id?.let { path.withArg(NEWS_ID, it) } ?: path)
}
