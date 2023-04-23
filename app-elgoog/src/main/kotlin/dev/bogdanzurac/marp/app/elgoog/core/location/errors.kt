package dev.bogdanzurac.marp.app.elgoog.core.location

import dev.bogdanzurac.marp.app.elgoog.R
import dev.bogdanzurac.marp.app.elgoog.core.arch.DialogManager.DialogContent
import dev.bogdanzurac.marp.app.elgoog.core.location.LocationException.LocationFailureException
import dev.bogdanzurac.marp.app.elgoog.core.location.LocationException.NoLocationException
import dev.bogdanzurac.marp.app.elgoog.core.ui.TextResource
import dev.bogdanzurac.marp.app.elgoog.core.ui.getGenericErrorDialogFor
import dev.bogdanzurac.marp.core.exception.AppException
import dev.bogdanzurac.marp.core.logger

sealed class LocationException : AppException() {
    object NoLocationException : LocationException()
    class LocationFailureException(val wrappedException: Throwable) : LocationException()
}

fun getLocationErrorDialogFor(exception: Throwable): DialogContent =
    when (exception) {
        is NoLocationException -> DialogContent(
            TextResource(R.string.error_title),
            TextResource(R.string.error_location_unavailable)
        )
        is LocationFailureException -> {
            logger.e("Could not get location", exception.wrappedException)
            DialogContent(
                TextResource(R.string.error_title),
                TextResource(R.string.error_location_failure)
            )
        }
        else -> getGenericErrorDialogFor(exception)
    }
