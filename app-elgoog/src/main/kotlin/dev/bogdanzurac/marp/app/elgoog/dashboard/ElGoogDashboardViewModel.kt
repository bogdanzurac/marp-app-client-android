package dev.bogdanzurac.marp.app.elgoog.dashboard

import dev.bogdanzurac.marp.app.elgoog.dashboard.ElgoogBottomNavigationItem.*
import dev.bogdanzurac.marp.core.auth.AuthManager
import dev.bogdanzurac.marp.core.feature.FeatureManager
import dev.bogdanzurac.marp.core.navigation.AppNavigator
import dev.bogdanzurac.marp.feature.dashboard.ui.BottomNavigationItem
import dev.bogdanzurac.marp.feature.dashboard.ui.DashboardViewModel
import org.koin.core.annotation.Factory

@Factory
class ElGoogDashboardViewModel(
    appNavigator: AppNavigator,
    authManager: AuthManager,
    featureManager: FeatureManager
) : DashboardViewModel(appNavigator, authManager, featureManager) {

    override val bottomNavigationItems: List<BottomNavigationItem> = listOf(
        CryptoNavigationItem,
        MoviesNavigationItem,
        NotesNavigationItem,
        NewsNavigationItem,
        WeatherNavigationItem
    )
}