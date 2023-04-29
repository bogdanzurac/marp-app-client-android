package dev.bogdanzurac.marp.core.ui.prompts

import dev.bogdanzurac.marp.core.prompts.DialogManager
import dev.bogdanzurac.marp.core.prompts.DialogManager.DialogAction
import dev.bogdanzurac.marp.core.prompts.DialogManager.DialogContent
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Singleton

@Singleton
class DialogManagerImpl : DialogManager {

    private val dialogsFlow: MutableSharedFlow<DialogContent?> =
        MutableSharedFlow(
            replay = 0,
            onBufferOverflow = BufferOverflow.DROP_OLDEST,
            extraBufferCapacity = 1
        )

    private val eventsFlow: MutableSharedFlow<DialogAction> =
        MutableSharedFlow(
            replay = 0,
            onBufferOverflow = BufferOverflow.DROP_OLDEST,
            extraBufferCapacity = 1
        )

    fun sendEvent(action: DialogAction) {
        dialogsFlow.tryEmit(null)
        eventsFlow.tryEmit(action)
    }

    fun observeDialogs(): Flow<DialogContent?> = dialogsFlow

    override suspend fun showDialog(dialogContent: DialogContent): DialogAction {
        dialogsFlow.tryEmit(dialogContent)
        return eventsFlow.first()
    }
}
