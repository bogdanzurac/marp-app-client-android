package dev.bogdanzurac.marp.app.elgoog.notes

import org.koin.core.annotation.Factory

@Factory
class CreateNoteUseCase(private val notesRepository: NotesRepository) {

    suspend operator fun invoke(note: Note): Result<Note> =
        notesRepository.createNote(note)
}