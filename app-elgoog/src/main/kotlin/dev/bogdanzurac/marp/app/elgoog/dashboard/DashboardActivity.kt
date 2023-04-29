package dev.bogdanzurac.marp.app.elgoog.dashboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dev.bogdanzurac.marp.app.elgoog.core.navigation.AppNavigator
import dev.bogdanzurac.marp.app.elgoog.core.theme.ElgoogTheme
import dev.bogdanzurac.marp.core.ui.prompts.PermissionManagerImpl
import org.koin.android.ext.android.get
import java.util.*

class DashboardActivity : ComponentActivity() {

    private val appNavigator: AppNavigator = get()
    private val permissionManager: PermissionManagerImpl = get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionManager.currentActivity = this
        Locale.setDefault(Locale.ENGLISH)
        setContent { ElgoogTheme { DashboardScreen(appNavigator) } }
    }

    override fun onDestroy() {
        super.onDestroy()
        permissionManager.currentActivity = null
    }
}
