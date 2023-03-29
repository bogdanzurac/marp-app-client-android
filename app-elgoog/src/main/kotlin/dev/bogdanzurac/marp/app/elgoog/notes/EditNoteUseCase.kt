package dev.bogdanzurac.marp.app.elgoog.notes

import org.koin.core.annotation.Factory

@Factory
class EditNoteUseCase(private val notesRepository: NotesRepository) {

    suspend operator fun invoke(note: Note): Result<Unit> =
        notesRepository.editNote(note)
}