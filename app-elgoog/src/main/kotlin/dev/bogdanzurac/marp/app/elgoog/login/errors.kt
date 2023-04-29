package dev.bogdanzurac.marp.app.elgoog.login

import dev.bogdanzurac.marp.app.elgoog.R
import dev.bogdanzurac.marp.core.TextResource
import dev.bogdanzurac.marp.core.auth.AuthException.AccountCreationException
import dev.bogdanzurac.marp.core.auth.AuthException.InvalidCredentialsException
import dev.bogdanzurac.marp.core.prompts.DialogManager
import dev.bogdanzurac.marp.core.prompts.DialogManager.DialogContent.InformationDialogContent
import dev.bogdanzurac.marp.core.ui.getGenericErrorDialogFor
import dev.bogdanzurac.marp.core.ui.R as coreUiR

fun getAuthErrorDialogFor(exception: Throwable): DialogManager.DialogContent =
    when (exception) {
        is AccountCreationException -> InformationDialogContent(
            TextResource(coreUiR.string.error_title),
            TextResource(R.string.error_account_creation)
        )
        is InvalidCredentialsException -> InformationDialogContent(
            TextResource(coreUiR.string.error_title),
            TextResource(R.string.error_auth_credentials)
        )
        else -> getGenericErrorDialogFor(exception)
    }
