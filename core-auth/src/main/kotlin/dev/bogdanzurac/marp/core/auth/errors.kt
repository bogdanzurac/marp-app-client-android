package dev.bogdanzurac.marp.core.auth

import dev.bogdanzurac.marp.core.exception.AppException

sealed class AuthException : AppException() {
    class AccountCreationException(val wrappedException: Throwable) : AuthException()
    class InvalidCredentialsException(val wrappedException: Throwable) : AuthException()
}
