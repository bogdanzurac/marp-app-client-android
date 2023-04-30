package dev.bogdanzurac.marp.app.elgoog.callbacks

import dev.bogdanzurac.marp.feature.crypto.ui.CryptoCallbacks
import dev.bogdanzurac.marp.feature.notes.ui.AddNote
import dev.bogdanzurac.marp.feature.notes.ui.NoteDetails
import org.koin.core.annotation.Single

@Single
class CryptoCallbacksImpl : CryptoCallbacks {

    override fun getAddNoteRoute(cryptoAssetId: String) = AddNote(cryptoAssetId)

    override fun getNoteDetailsRoute(id: String) = NoteDetails(id)
}