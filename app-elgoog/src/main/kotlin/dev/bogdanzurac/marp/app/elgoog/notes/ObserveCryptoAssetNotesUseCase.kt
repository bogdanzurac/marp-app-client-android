package dev.bogdanzurac.marp.app.elgoog.notes

import dev.bogdanzurac.marp.core.mapResult
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
class ObserveCryptoAssetNotesUseCase(private val notesRepository: NotesRepository) {

    operator fun invoke(assetId: String): Flow<Result<List<Note>>> =
        notesRepository.observeNotes(listOf(Triple("cryptoId", true, assetId)))
            .mapResult { notes -> notes.filter { it.cryptoId == assetId } }
}