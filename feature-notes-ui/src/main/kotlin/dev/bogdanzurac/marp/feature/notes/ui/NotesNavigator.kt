package dev.bogdanzurac.marp.feature.notes.ui

import dev.bogdanzurac.marp.core.navigation.FeatureNavigator
import org.koin.core.annotation.Single

@Single
class NotesNavigator(private val callbacks: NotesCallbacks) : FeatureNavigator() {

    fun navigateToAddNote() = navigateTo(AddNote)

    fun navigateToNoteDetails(id: String) = navigateTo(NoteDetails(id))

    fun navigateToLogin() = navigateTo(callbacks.getLoginRoute())
}
