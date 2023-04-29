package dev.bogdanzurac.marp.app.elgoog.login

import dev.bogdanzurac.marp.app.elgoog.R
import dev.bogdanzurac.marp.app.elgoog.core.arch.DialogManager
import dev.bogdanzurac.marp.app.elgoog.core.ui.TextResource
import dev.bogdanzurac.marp.app.elgoog.core.ui.getGenericErrorDialogFor
import dev.bogdanzurac.marp.core.auth.AuthException.AccountCreationException
import dev.bogdanzurac.marp.core.auth.AuthException.InvalidCredentialsException

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
