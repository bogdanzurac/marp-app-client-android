package dev.bogdanzurac.marp.app.elgoog.news

import androidx.navigation.NavGraphBuilder
import dev.bogdanzurac.marp.app.elgoog.core.navigation.composable
import dev.bogdanzurac.marp.app.elgoog.core.navigation.getStringArg
import dev.bogdanzurac.marp.app.elgoog.core.navigation.navigation
import dev.bogdanzurac.marp.app.elgoog.news.NewsDetails.NEWS_ID

fun NavGraphBuilder.newsNavGraph() {
    navigation(
        startDestination = NewsList,
        route = News
    ) {
        composable(NewsList) { NewsListScreen() }
        composable(NewsDetails) { NewsDetailsScreen(it.getStringArg(NEWS_ID)!!) }
    }
}