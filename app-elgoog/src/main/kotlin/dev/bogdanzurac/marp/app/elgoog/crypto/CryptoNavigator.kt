package dev.bogdanzurac.marp.app.elgoog.crypto

import dev.bogdanzurac.marp.app.elgoog.core.navigation.FeatureNavigator
import dev.bogdanzurac.marp.app.elgoog.notes.AddNote
import dev.bogdanzurac.marp.app.elgoog.notes.NoteDetails
import org.koin.core.annotation.Single

@Single
class CryptoNavigator : FeatureNavigator() {

    fun navigateToCryptoDetails(id: String) = navigateTo(CryptoDetails(id))

    fun navigateToNoteDetails(id: String) = navigateTo(NoteDetails(id))

    fun navigateToAddNote(cryptoAssetId: String) = navigateTo(AddNote(cryptoAssetId))
}
