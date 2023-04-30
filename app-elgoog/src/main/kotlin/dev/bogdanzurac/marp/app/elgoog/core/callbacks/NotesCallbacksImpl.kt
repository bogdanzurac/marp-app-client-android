package dev.bogdanzurac.marp.app.elgoog.core.callbacks

import dev.bogdanzurac.marp.app.elgoog.login.Auth
import dev.bogdanzurac.marp.feature.notes.ui.NotesCallbacks
import org.koin.core.annotation.Single

@Single
class NotesCallbacksImpl : NotesCallbacks {

    override fun getLoginRoute() = Auth
}