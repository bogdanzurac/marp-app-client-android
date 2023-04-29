package dev.bogdanzurac.marp.core.prompts

import dev.bogdanzurac.marp.core.TextResource

interface DialogManager {

    suspend fun showDialog(dialogContent: DialogContent): DialogAction

    sealed interface DialogContent {

        data class InformationDialogContent(
            var title: TextResource,
            var body: TextResource,
            var button: TextResource = TextResource(R.string.button_ok),
        ) : DialogContent

        data class ConfirmationDialogContent(
            var title: TextResource,
            var body: TextResource,
            var primaryButton: TextResource = TextResource(R.string.button_ok),
            var secondaryButton: TextResource = TextResource(R.string.button_cancel),
        ) : DialogContent
    }

    enum class DialogAction {
        PRIMARY,
        SECONDARY,
    }
}
