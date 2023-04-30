package dev.bogdanzurac.marp.app.elgoog.notes

import dev.bogdanzurac.marp.core.auth.AuthManager
import dev.bogdanzurac.marp.lib.db.firebase.FirebaseDatabase
import dev.bogdanzurac.marp.core.combineResult
import dev.bogdanzurac.marp.core.mapResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single

@Single
class NotesRepository(
    private val authManager: AuthManager,
    private val firebaseDatabase: FirebaseDatabase
) {

    private fun userId(): Flow<String?> = authManager.observeUser().map { it?.id }

    fun observeNotes(filtering: List<Triple<String, Boolean, String>> = emptyList()): Flow<Result<List<Note>>> =
        firebaseDatabase.observeData<NoteModel>(NOTES_COLLECTION_NAME, filtering)
            .combineResult(userId().map { Result.success(it) })
            .mapResult { result ->
                result.first.map { note ->
                    note.toNote(isEditable = note.userId == result.second)
                }
            }
            .flowOn(Dispatchers.IO)

    suspend fun createNote(note: Note): Result<Note> =
        withContext(Dispatchers.IO) {
            firebaseDatabase.addData(NOTES_COLLECTION_NAME, note.toNoteModel())
                .map { it.toNote(isEditable = true) }
        }

    suspend fun editNote(note: Note): Result<Unit> =
        withContext(Dispatchers.IO) {
            firebaseDatabase.editData(NOTES_COLLECTION_NAME, note.id, note.toNoteModel())
        }

    suspend fun deleteNote(id: String): Result<Unit> =
        withContext(Dispatchers.IO) {
            firebaseDatabase.deleteData(NOTES_COLLECTION_NAME, id)
        }

    companion object {
        private const val NOTES_COLLECTION_NAME = "notes"
    }
}