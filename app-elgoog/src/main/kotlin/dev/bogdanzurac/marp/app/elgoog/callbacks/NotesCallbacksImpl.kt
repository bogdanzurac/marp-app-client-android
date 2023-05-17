package dev.bogdanzurac.marp.app.elgoog.callbacks

import dev.bogdanzurac.marp.feature.auth.ui.Auth
import dev.bogdanzurac.marp.feature.notes.ui.NotesCallbacks
import org.koin.core.annotation.Single

@Single
class NotesCallbacksImpl : NotesCallbacks {

    override fun getLoginRoute() = Auth

    override fun shouldShowAllNotes(): Boolean = false
}