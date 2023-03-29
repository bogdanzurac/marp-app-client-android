package dev.bogdanzurac.marp.app.elgoog.notes

import dev.bogdanzurac.marp.app.elgoog.core.navigation.FeatureNavigator
import dev.bogdanzurac.marp.app.elgoog.login.Auth
import org.koin.core.annotation.Single

@Single
class NotesNavigator : FeatureNavigator() {

    fun navigateToAddNote() = navigateTo(AddNote)

    fun navigateToNoteDetails(id: String) = navigateTo(NoteDetails(id))

    fun navigateToLogin() = navigateTo(Auth)
}
