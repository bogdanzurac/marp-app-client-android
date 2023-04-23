package dev.bogdanzurac.marp.core.exception

data class NetworkException(private val httpStatusCode: Int? = null) : AppException() {

    override val message: String
        get() = "Network exception $httpStatusCode"
}