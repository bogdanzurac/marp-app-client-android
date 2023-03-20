package dev.bogdanzurac.marp.app.elgoog.news

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import dev.bogdanzurac.marp.app.elgoog.core.navigation.stringArg
import dev.bogdanzurac.marp.app.elgoog.news.NewsRoute.NewsDetails.Companion.NEWS_ID_ARG

fun NavGraphBuilder.newsNavGraph() {
    navigation(
        startDestination = NewsRoute.NewsList.path,
        route = NewsRoute.Root.path
    ) {
        composable(NewsRoute.NewsList.path) { NewsListScreen() }
        composable(
            NewsRoute.NewsDetails().path,
            listOf(stringArg(NEWS_ID_ARG))
        ) { NewsDetailsScreen(it.arguments?.getString(NEWS_ID_ARG)!!) }
    }
}