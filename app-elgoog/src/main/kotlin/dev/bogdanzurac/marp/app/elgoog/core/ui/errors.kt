package dev.bogdanzurac.marp.app.elgoog.core.ui

import dev.bogdanzurac.marp.app.elgoog.R
import dev.bogdanzurac.marp.app.elgoog.core.arch.DialogManager.DialogContent
import dev.bogdanzurac.marp.core.exception.DataLoadingException
import dev.bogdanzurac.marp.core.exception.NetworkException
import dev.bogdanzurac.marp.core.exception.NotAvailableException
import dev.bogdanzurac.marp.core.exception.UnknownApiResponseException

fun getGenericErrorDialogFor(throwable: Throwable): DialogContent =
    when (throwable) {
        is DataLoadingException -> DialogContent(
            TextResource(R.string.error_title),
            TextResource(R.string.error_loading)
        )
        is NetworkException -> DialogContent(
            TextResource(R.string.error_title),
            TextResource(R.string.error_network)
        )
        is NotAvailableException -> DialogContent(
            TextResource(R.string.error_title),
            TextResource(R.string.error_not_available)
        )
        is UnknownApiResponseException -> DialogContent(
            TextResource(R.string.error_title, throwable.httpStatusCode.toString()),
            TextResource(R.string.error_unknown)
        )
        else -> DialogContent(
            TextResource(R.string.error_title),
            TextResource(R.string.error_general)
        )
    }