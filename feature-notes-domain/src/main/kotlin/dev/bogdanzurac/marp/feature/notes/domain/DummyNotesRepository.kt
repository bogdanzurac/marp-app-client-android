package dev.bogdanzurac.marp.feature.notes.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class DummyNotesRepository : NotesRepository {

    override fun observeNotes(
        filtering: List<Triple<String, Boolean, String>>
    ): Flow<Result<List<Note>>> = emptyFlow()

    override suspend fun createNote(note: Note): Result<Note> =
        Result.failure(NotImplementedError())

    override suspend fun editNote(note: Note): Result<Unit> =
        Result.failure(NotImplementedError())

    override suspend fun deleteNote(id: String): Result<Unit> =
        Result.failure(NotImplementedError())
}