package dev.bogdanzurac.marp.feature.news.ui

import dev.bogdanzurac.marp.core.navigation.FeatureNavigator
import org.koin.core.annotation.Single

@Single
class NewsNavigator : FeatureNavigator() {

    fun navigateToNewsDetails(id: String) = navigateTo(NewsDetails(id))
}
