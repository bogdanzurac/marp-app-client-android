package dev.bogdanzurac.marp.feature.notes.domain

import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    fun observeNotes(filtering: List<Triple<String, Boolean, String>> = emptyList()): Flow<Result<List<Note>>>

    suspend fun createNote(note: Note): Result<Note>

    suspend fun editNote(note: Note): Result<Unit>

    suspend fun deleteNote(id: String): Result<Unit>
}