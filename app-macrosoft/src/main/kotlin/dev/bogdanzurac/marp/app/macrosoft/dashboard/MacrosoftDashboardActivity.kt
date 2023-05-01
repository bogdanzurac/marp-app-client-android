package dev.bogdanzurac.marp.app.macrosoft.dashboard

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import dev.bogdanzurac.marp.app.macrosoft.theme.MacrosoftTheme
import dev.bogdanzurac.marp.core.navigation.AppRoute
import dev.bogdanzurac.marp.feature.crypto.ui.Crypto
import dev.bogdanzurac.marp.feature.crypto.ui.cryptoNavGraph
import dev.bogdanzurac.marp.feature.dashboard.ui.DashboardActivity
import dev.bogdanzurac.marp.feature.news.ui.newsNavGraph
import dev.bogdanzurac.marp.feature.weather.ui.weatherNavGraph
import java.util.*

class MacrosoftDashboardActivity : DashboardActivity() {

    @Composable
    override fun AppTheme(content: @Composable () -> Unit) {
        MacrosoftTheme { content() }
    }

    override val navGraphBuilder: NavGraphBuilder.() -> Unit = {
        cryptoNavGraph()
        newsNavGraph()
        weatherNavGraph()
    }

    override val startRoute: AppRoute = Crypto

    override val locale: Locale = Locale("ro")
}
