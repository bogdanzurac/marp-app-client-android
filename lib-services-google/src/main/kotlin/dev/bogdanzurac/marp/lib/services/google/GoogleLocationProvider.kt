package dev.bogdanzurac.marp.lib.services.google

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationServices
import dev.bogdanzurac.marp.core.flatMap
import dev.bogdanzurac.marp.core.logger
import dev.bogdanzurac.marp.core.prompts.PermissionManager
import dev.bogdanzurac.marp.core.recoverRethrow
import dev.bogdanzurac.marp.core.services.Location
import dev.bogdanzurac.marp.core.services.LocationException.LocationFailureException
import dev.bogdanzurac.marp.core.services.LocationException.NoLocationException
import dev.bogdanzurac.marp.core.services.LocationProvider
import kotlinx.coroutines.tasks.await

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
        runCatching {
            fusedLocationClient.lastLocation.await()?.let {
                logger.d("New location received $it")
                Location(it.latitude, it.longitude)
            } ?: throw NoLocationException
        }
            .recoverRethrow {
                logger.e("Error while waiting for location", it)
                LocationFailureException(it)
            }

}
