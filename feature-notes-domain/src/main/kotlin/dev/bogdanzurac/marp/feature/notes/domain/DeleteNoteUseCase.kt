package dev.bogdanzurac.marp.feature.notes.domain

import org.koin.core.annotation.Factory

@Factory
class DeleteNoteUseCase(private val notesRepository: NotesRepository) {

    suspend operator fun invoke(id: String): Result<Unit> =
        notesRepository.deleteNote(id)
}