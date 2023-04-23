package dev.bogdanzurac.marp.core.exception

data class UnknownApiResponseException(val httpStatusCode: Int? = null) : AppException() {

    override val message: String
        get() = "Unknown API response $httpStatusCode"
}