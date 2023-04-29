package dev.bogdanzurac.marp.core.prompts

import dev.bogdanzurac.marp.core.exception.AppException

interface PermissionManager {

    suspend fun requestLocationPermission(): Result<Unit>
}

object PermissionException : AppException()