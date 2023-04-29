package dev.bogdanzurac.marp.core.ui.prompts

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager.PERMISSION_GRANTED
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.core.content.ContextCompat.checkSelfPermission
import dev.bogdanzurac.marp.core.prompts.PermissionException
import dev.bogdanzurac.marp.core.prompts.PermissionManager
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Singleton
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

@Singleton
class PermissionManagerImpl : PermissionManager {

    var currentActivity: ComponentActivity? = null
        set(value) {
            field = value
            registerListener()
        }

    private var listener: ActivityResultLauncher<String>? = null

    private val resultFlow: MutableSharedFlow<Result<Unit>> = MutableSharedFlow(replay = 1)

    override suspend fun requestLocationPermission(): Result<Unit> =
        checkPermission(ACCESS_FINE_LOCATION)
            .map { granted -> if (!granted) requestPermission(ACCESS_FINE_LOCATION) }

    private fun registerListener() {
        listener = currentActivity?.registerForActivityResult(RequestPermission()) { granted ->
            if (granted) resultFlow.tryEmit(success(Unit))
            else resultFlow.tryEmit(failure(PermissionException))
        }
    }

    private suspend fun requestPermission(permission: String): Result<Unit> =
        listener
            ?.launch(permission)
            ?.let { resultFlow.first() }
            ?: failure(PermissionException)


    private fun checkPermission(permission: String): Result<Boolean> =
        currentActivity?.let {
            success(checkSelfPermission(it, permission) == PERMISSION_GRANTED)
        } ?: failure(PermissionException)
}
