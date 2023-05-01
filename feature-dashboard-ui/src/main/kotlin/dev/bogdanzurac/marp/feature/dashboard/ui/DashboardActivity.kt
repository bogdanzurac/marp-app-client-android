package dev.bogdanzurac.marp.feature.dashboard.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import dev.bogdanzurac.marp.core.navigation.AppNavigator
import dev.bogdanzurac.marp.core.navigation.AppRoute
import dev.bogdanzurac.marp.core.ui.prompts.PermissionManagerImpl
import org.koin.android.ext.android.get
import java.util.*

abstract class DashboardActivity : ComponentActivity() {

    @Composable
    abstract fun AppTheme(content: @Composable () -> Unit)

    abstract val navGraphBuilder: NavGraphBuilder.() -> Unit

    abstract val startRoute: AppRoute

    abstract val locale: Locale

    private val appNavigator: AppNavigator = get()
    private val permissionManager: PermissionManagerImpl = get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionManager.currentActivity = this
        Locale.setDefault(locale)
        setContent { AppTheme { DashboardScreen(startRoute, navGraphBuilder, appNavigator) } }
    }

    override fun onDestroy() {
        super.onDestroy()
        permissionManager.currentActivity = null
    }
}
