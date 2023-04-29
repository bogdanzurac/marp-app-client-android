package dev.bogdanzurac.marp.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import dev.bogdanzurac.marp.core.TextResource
import dev.bogdanzurac.marp.core.TextResource.StringIdTextResource

@Composable
fun TextResource.getText(): String = when (this) {
    is StringIdTextResource -> {
        if (args.isEmpty()) stringResource(stringResId)
        else stringResource(stringResId, *args)
    }
}
