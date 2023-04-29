package dev.bogdanzurac.marp.core.ui

import dev.bogdanzurac.marp.core.TextResource
import dev.bogdanzurac.marp.core.exception.DataLoadingException
import dev.bogdanzurac.marp.core.exception.NetworkException
import dev.bogdanzurac.marp.core.exception.NotAvailableException
import dev.bogdanzurac.marp.core.exception.UnknownApiResponseException
import dev.bogdanzurac.marp.core.prompts.DialogManager.DialogContent
import dev.bogdanzurac.marp.core.prompts.DialogManager.DialogContent.InformationDialogContent

fun getGenericErrorDialogFor(throwable: Throwable): DialogContent =
    when (throwable) {
        is DataLoadingException -> InformationDialogContent(
            TextResource(R.string.error_title),
            TextResource(R.string.error_loading)
        )
        is NetworkException -> InformationDialogContent(
            TextResource(R.string.error_title),
            TextResource(R.string.error_network)
        )
        is NotAvailableException -> InformationDialogContent(
            TextResource(R.string.error_title),
            TextResource(R.string.error_not_available)
        )
        is UnknownApiResponseException -> InformationDialogContent(
            TextResource(R.string.error_title, throwable.httpStatusCode.toString()),
            TextResource(R.string.error_unknown)
        )
        else -> InformationDialogContent(
            TextResource(R.string.error_title),
            TextResource(R.string.error_general)
        )
    }