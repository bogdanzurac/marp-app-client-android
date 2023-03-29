package dev.bogdanzurac.marp.app.elgoog.core.auth

import dev.bogdanzurac.marp.app.elgoog.R
import dev.bogdanzurac.marp.app.elgoog.core.arch.DialogManager
import dev.bogdanzurac.marp.app.elgoog.core.auth.AuthException.AccountCreationException
import dev.bogdanzurac.marp.app.elgoog.core.auth.AuthException.InvalidCredentialsException
import dev.bogdanzurac.marp.app.elgoog.core.exception.AppException
import dev.bogdanzurac.marp.app.elgoog.core.ui.TextResource
import dev.bogdanzurac.marp.app.elgoog.core.ui.getGenericErrorDialogFor

sealed class AuthException : AppException() {
    class AccountCreationException(val wrappedException: Throwable) : AuthException()
    class InvalidCredentialsException(val wrappedException: Throwable) : AuthException()
}

fun getAuthErrorDialogFor(exception: Throwable): DialogManager.DialogContent =
    when (exception) {
        is AccountCreationException -> DialogManager.DialogContent(
            TextResource(R.string.error_title),
            TextResource(R.string.error_account_creation)
        )
        is InvalidCredentialsException -> DialogManager.DialogContent(
            TextResource(R.string.error_title),
            TextResource(R.string.error_auth_credentials)
        )
        else -> getGenericErrorDialogFor(exception)
    }
