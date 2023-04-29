package dev.bogdanzurac.marp.core.services

import dev.bogdanzurac.marp.core.TextResource
import dev.bogdanzurac.marp.core.exception.AppException
import dev.bogdanzurac.marp.core.logger
import dev.bogdanzurac.marp.core.prompts.DialogManager.DialogContent
import dev.bogdanzurac.marp.core.prompts.DialogManager.DialogContent.InformationDialogContent
import dev.bogdanzurac.marp.core.services.LocationException.LocationFailureException
import dev.bogdanzurac.marp.core.services.LocationException.NoLocationException
import dev.bogdanzurac.marp.core.ui.getGenericErrorDialogFor
import dev.bogdanzurac.marp.core.ui.R as coreUiR

sealed class LocationException : AppException() {
    object NoLocationException : LocationException()
    class LocationFailureException(val wrappedException: Throwable) : LocationException()
}

fun getLocationErrorDialogFor(exception: Throwable): DialogContent =
    when (exception) {
        is NoLocationException -> InformationDialogContent(
            TextResource(coreUiR.string.error_title),
            TextResource(R.string.error_location_unavailable)
        )
        is LocationFailureException -> {
            logger.e("Could not get location", exception.wrappedException)
            InformationDialogContent(
                TextResource(coreUiR.string.error_title),
                TextResource(R.string.error_location_failure)
            )
        }
        else -> getGenericErrorDialogFor(exception)
    }
