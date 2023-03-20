package dev.bogdanzurac.marp.app.elgoog.core.ui

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

/**
 * Abstract representation of a Text. Various implementations wrap different text sources
 * (e.g. pure strings, texts pulled from localized string resources etc.).
 */
interface TextResource {

    @Composable
    fun getText(): String

    companion object {

        /**
         * Creates a [TextResource] based on a localized string resource.
         */
        operator fun invoke(@StringRes resId: Int, vararg args: Any): TextResource =
            StringIdTextResource(resId, args)
    }

    private class StringIdTextResource(
        @StringRes val stringResId: Int,
        val args: Array<out Any>,
    ) : TextResource {

        @Composable
        override fun getText(): String =
            if (args.isEmpty()) stringResource(stringResId)
            else stringResource(stringResId, *args)
    }
}