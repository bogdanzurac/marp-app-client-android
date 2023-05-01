package dev.bogdanzurac.marp.app.macrosoft.dashboard

import dev.bogdanzurac.marp.app.macrosoft.dashboard.MacrosoftBottomNavigationItem.*
import dev.bogdanzurac.marp.core.auth.AuthManager
import dev.bogdanzurac.marp.core.feature.FeatureManager
import dev.bogdanzurac.marp.core.navigation.AppNavigator
import dev.bogdanzurac.marp.feature.crypto.domain.CryptoFeature
import dev.bogdanzurac.marp.feature.dashboard.ui.BottomNavigationItem
import dev.bogdanzurac.marp.feature.dashboard.ui.DashboardViewModel
import dev.bogdanzurac.marp.feature.news.domain.NewsFeature
import dev.bogdanzurac.marp.feature.weather.domain.WeatherFeature
import org.koin.core.annotation.Factory

@Factory
class MacrosoftDashboardViewModel(
    appNavigator: AppNavigator,
    authManager: AuthManager,
    featureManager: FeatureManager
) : DashboardViewModel(appNavigator, authManager, featureManager) {

    override val bottomNavigationItems: List<BottomNavigationItem> = buildList {
        if (featureManager.isEnabled(CryptoFeature))
            add(CryptoNavigationItem)
        if (featureManager.isEnabled(NewsFeature))
            add(NewsNavigationItem)
        if (featureManager.isEnabled(WeatherFeature))
            add(WeatherNavigationItem)
    }
}