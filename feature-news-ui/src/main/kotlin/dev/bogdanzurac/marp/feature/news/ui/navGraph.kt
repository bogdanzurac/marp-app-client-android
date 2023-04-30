package dev.bogdanzurac.marp.feature.news.ui

import androidx.navigation.NavGraphBuilder
import dev.bogdanzurac.marp.core.navigation.composable
import dev.bogdanzurac.marp.core.navigation.getStringArg
import dev.bogdanzurac.marp.core.navigation.navigation
import dev.bogdanzurac.marp.feature.news.ui.NewsDetails.NEWS_ID

fun NavGraphBuilder.newsNavGraph() {
    navigation(
        startDestination = NewsList,
        route = News
    ) {
        composable(NewsList) { NewsListScreen() }
        composable(NewsDetails) { NewsDetailsScreen(it.getStringArg(NEWS_ID)!!) }
    }
}