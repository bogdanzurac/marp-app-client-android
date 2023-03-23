package dev.bogdanzurac.marp.app.elgoog.core.location

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationServices
import dev.bogdanzurac.marp.app.elgoog.core.arch.PermissionManager
import dev.bogdanzurac.marp.app.elgoog.core.flatMap
import dev.bogdanzurac.marp.app.elgoog.core.location.LocationException.LocationFailureException
import dev.bogdanzurac.marp.app.elgoog.core.location.LocationException.NoLocationException
import dev.bogdanzurac.marp.app.elgoog.core.logger
import kotlinx.coroutines.suspendCancellableCoroutine
import org.koin.core.annotation.Single
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success
import kotlin.coroutines.resume

@Single
class GoogleLocationProvider(
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
                    logger.d("New location received $location")
                    location?.let {
                        continuation.resume(success(Location(it.latitude, it.longitude)))
                    } ?: continuation.resume(failure(NoLocationException))
                }
                .addOnFailureListener {
                    logger.e("Error while waiting for location", it)
                    continuation.resume(failure(LocationFailureException(it)))
                }
        }
}


