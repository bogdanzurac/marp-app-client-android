package dev.bogdanzurac.marp.app.elgoog.dashboard

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import dev.bogdanzurac.marp.app.elgoog.theme.ElgoogTheme
import dev.bogdanzurac.marp.core.navigation.AppRoute
import dev.bogdanzurac.marp.feature.auth.ui.authNavGraph
import dev.bogdanzurac.marp.feature.crypto.ui.Crypto
import dev.bogdanzurac.marp.feature.crypto.ui.cryptoNavGraph
import dev.bogdanzurac.marp.feature.dashboard.ui.DashboardActivity
import dev.bogdanzurac.marp.feature.movies.ui.moviesNavGraph
import dev.bogdanzurac.marp.feature.news.ui.newsNavGraph
import dev.bogdanzurac.marp.feature.notes.ui.notesNavGraph
import dev.bogdanzurac.marp.feature.weather.ui.weatherNavGraph
import java.util.*

class ElGoogDashboardActivity : DashboardActivity() {

    @Composable
    override fun AppTheme(content: @Composable () -> Unit) {
        ElgoogTheme { content() }
    }

    override val navGraphBuilder: NavGraphBuilder.() -> Unit = {
        authNavGraph()
        cryptoNavGraph()
        moviesNavGraph()
        newsNavGraph()
        notesNavGraph()
        weatherNavGraph()
    }

    override val startRoute: AppRoute = Crypto

    override val locale: Locale = Locale.ENGLISH
}
