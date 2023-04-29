package dev.bogdanzurac.marp.lib.services.huawei

import android.annotation.SuppressLint
import android.content.Context
import com.huawei.hms.location.LocationServices
import dev.bogdanzurac.marp.core.flatMap
import dev.bogdanzurac.marp.core.logger
import dev.bogdanzurac.marp.core.prompts.PermissionManager
import dev.bogdanzurac.marp.core.services.Location
import dev.bogdanzurac.marp.core.services.LocationException.LocationFailureException
import dev.bogdanzurac.marp.core.services.LocationException.NoLocationException
import dev.bogdanzurac.marp.core.services.LocationProvider
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success
import kotlin.coroutines.resume

class HuaweiLocationProvider(
    context: Context,
    private val permissionManager: PermissionManager
) : LocationProvider {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    override suspend fun getLocation(): Result<Location> =
        permissionManager.requestLocationPermission()
            .flatMap { getLastLocation() }

    @SuppressLint("MissingPermission")
    private suspend fun getLastLocation(): Result<Location> =
        suspendCancellableCoroutine { continuation ->
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: android.location.Location? ->
                    logger.d("New Huawei location received $location")
                    location?.let {
                        continuation.resume(success(Location(it.latitude, it.longitude)))
                    } ?: continuation.resume(failure(NoLocationException))
                }
                .addOnFailureListener {
                    logger.e("Error while waiting for Huawei location", it)
                    continuation.resume(failure(LocationFailureException(it)))
                }
        }
}
