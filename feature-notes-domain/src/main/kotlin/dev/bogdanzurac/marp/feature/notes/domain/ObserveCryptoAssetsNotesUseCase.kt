package dev.bogdanzurac.marp.feature.notes.domain

import dev.bogdanzurac.marp.core.mapResult
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Singleton

@Singleton
class ObserveCryptoAssetsNotesUseCase(private val notesRepository: NotesRepository) {

    operator fun invoke(): Flow<Result<List<Note>>> =
        notesRepository.observeNotes(listOf(Triple("cryptoId", false, "")))
            .mapResult { notes -> notes.filter { it.cryptoId?.isNotBlank() == true } }
}