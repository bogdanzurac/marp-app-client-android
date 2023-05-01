package dev.bogdanzurac.marp.app.elgoog.dashboard

import dev.bogdanzurac.marp.app.elgoog.dashboard.ElgoogBottomNavigationItem.*
import dev.bogdanzurac.marp.core.auth.AuthManager
import dev.bogdanzurac.marp.core.feature.FeatureManager
import dev.bogdanzurac.marp.core.navigation.AppNavigator
import dev.bogdanzurac.marp.feature.crypto.domain.CryptoFeature
import dev.bogdanzurac.marp.feature.dashboard.ui.BottomNavigationItem
import dev.bogdanzurac.marp.feature.dashboard.ui.DashboardViewModel
import dev.bogdanzurac.marp.feature.movies.domain.MoviesFeature
import dev.bogdanzurac.marp.feature.news.domain.NewsFeature
import dev.bogdanzurac.marp.feature.notes.domain.NotesFeature
import dev.bogdanzurac.marp.feature.weather.domain.WeatherFeature
import org.koin.core.annotation.Factory

@Factory
class ElGoogDashboardViewModel(
    appNavigator: AppNavigator,
    authManager: AuthManager,
    featureManager: FeatureManager
) : DashboardViewModel(appNavigator, authManager, featureManager) {

    override val bottomNavigationItems: List<BottomNavigationItem> = buildList {
        if (featureManager.isEnabled(CryptoFeature))
            add(CryptoNavigationItem)
        if (featureManager.isEnabled(MoviesFeature))
            add(MoviesNavigationItem)
        if (featureManager.isEnabled(NotesFeature))
            add(NotesNavigationItem)
        if (featureManager.isEnabled(NewsFeature))
            add(NewsNavigationItem)
        if (featureManager.isEnabled(WeatherFeature))
            add(WeatherNavigationItem)
    }
}