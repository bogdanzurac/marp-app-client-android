package dev.bogdanzurac.marp.app.elgoog.core.location

interface LocationProvider {

    suspend fun getLocation(): Result<Location>
}

data class Location(
    val lat: Double,
    val long: Double
)
