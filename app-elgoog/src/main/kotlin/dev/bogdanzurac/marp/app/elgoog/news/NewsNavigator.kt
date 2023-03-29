package dev.bogdanzurac.marp.app.elgoog.news

import dev.bogdanzurac.marp.app.elgoog.core.navigation.FeatureNavigator
import org.koin.core.annotation.Single

@Single
class NewsNavigator : FeatureNavigator() {

    fun navigateToNewsDetails(id: String) = navigateTo(NewsDetails(id))
}
