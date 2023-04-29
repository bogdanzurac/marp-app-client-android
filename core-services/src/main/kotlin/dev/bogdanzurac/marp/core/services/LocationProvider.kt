package dev.bogdanzurac.marp.core.services

interface LocationProvider {

    suspend fun getLocation(): Result<Location>
}

data class Location(
    val lat: Double,
    val long: Double
)
