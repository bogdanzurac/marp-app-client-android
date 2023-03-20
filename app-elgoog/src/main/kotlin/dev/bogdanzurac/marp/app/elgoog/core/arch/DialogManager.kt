package dev.bogdanzurac.marp.app.elgoog.core.arch

import dev.bogdanzurac.marp.app.elgoog.R
import dev.bogdanzurac.marp.app.elgoog.core.ui.TextResource
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Singleton

@Singleton
class DialogManager {

    private val dialogsFlow: MutableSharedFlow<DialogContent?> =
        MutableSharedFlow(
            replay = 0,
            onBufferOverflow = BufferOverflow.DROP_OLDEST,
            extraBufferCapacity = 1
        )

    private val eventsFlow: MutableSharedFlow<Unit> =
        MutableSharedFlow(
            replay = 0,
            onBufferOverflow = BufferOverflow.DROP_OLDEST,
            extraBufferCapacity = 1
        )

    fun sendEvent() {
        dialogsFlow.tryEmit(null)
        eventsFlow.tryEmit(Unit)
    }

    fun observeDialogs(): Flow<DialogContent?> = dialogsFlow

    suspend fun showDialog(dialogContent: DialogContent) {
        dialogsFlow.tryEmit(dialogContent)
        eventsFlow.first()
    }

    data class DialogContent(
        var title: TextResource,
        var body: TextResource,
        var button: TextResource = TextResource(R.string.button_ok),
    )
}
