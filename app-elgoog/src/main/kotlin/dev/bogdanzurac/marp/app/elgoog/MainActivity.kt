package dev.bogdanzurac.marp.app.elgoog

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dev.bogdanzurac.marp.app.elgoog.core.arch.DialogManager
import dev.bogdanzurac.marp.app.elgoog.core.arch.PermissionManager
import dev.bogdanzurac.marp.app.elgoog.core.feature.FeatureManager
import dev.bogdanzurac.marp.app.elgoog.core.navigation.*
import dev.bogdanzurac.marp.app.elgoog.core.theme.ElgoogTheme
import dev.bogdanzurac.marp.app.elgoog.core.ui.composable.AppDialog
import org.koin.android.ext.android.get
import java.util.*

class MainActivity : ComponentActivity() {

    private val appNavigator: AppNavigator = get()
    private val dialogManager: DialogManager = get()
    private val featureManager: FeatureManager = get()
    private val permissionManager: PermissionManager = get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionManager.currentActivity = this
        Locale.setDefault(Locale.ENGLISH)
        setContent {
            ElgoogTheme {
                val navController = rememberNavController()
                appNavigator.onCreate(navController)
                // Wait until features are available
                featureManager.isReady()
                    .collectAsStateWithLifecycle(false)
                    .value
                    .let { isReady ->
                        if (isReady) ContentView(navController, appNavigator, dialogManager)
                    }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        permissionManager.currentActivity = null
        appNavigator.onDestroy()
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ElgoogTheme {
        Greeting("Android")
    }
}