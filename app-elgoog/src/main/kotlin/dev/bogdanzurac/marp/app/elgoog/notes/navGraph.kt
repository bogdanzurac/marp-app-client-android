package dev.bogdanzurac.marp.app.elgoog.notes

import androidx.navigation.NavGraphBuilder
import dev.bogdanzurac.marp.core.navigation.composable
import dev.bogdanzurac.marp.core.navigation.getStringArg
import dev.bogdanzurac.marp.core.navigation.navigation
import dev.bogdanzurac.marp.app.elgoog.notes.AddNote.CRYPTO_ID
import dev.bogdanzurac.marp.app.elgoog.notes.NoteDetails.NOTE_ID

fun NavGraphBuilder.notesNavGraph() {
    navigation(
        startDestination = NotesList,
        route = Notes
    ) {
        composable(NotesList) { NotesListScreen() }
        composable(NoteDetails) { NoteDetailsScreen(it.getStringArg(NOTE_ID)) }
        composable(AddNote) {
            NoteDetailsScreen(
                noteId = null,
                cryptoId = it.getStringArg(CRYPTO_ID)
            )
        }
    }
}