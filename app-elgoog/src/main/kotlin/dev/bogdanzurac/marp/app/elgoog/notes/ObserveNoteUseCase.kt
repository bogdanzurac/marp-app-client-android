package dev.bogdanzurac.marp.app.elgoog.notes

import dev.bogdanzurac.marp.app.elgoog.core.combineResult
import dev.bogdanzurac.marp.app.elgoog.core.mapResultCatching
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import org.koin.core.annotation.Factory

@Factory
class ObserveNoteUseCase(
    private val observeUserNotesUseCase: ObserveUserNotesUseCase,
    private val observeCryptoAssetsNotesUseCase: ObserveCryptoAssetsNotesUseCase,
) {

    operator fun invoke(id: String): Flow<Result<Note>> =
        observeUserNotesUseCase()
            .combineResult(observeCryptoAssetsNotesUseCase())
            .mapResultCatching { notes ->
                notes.first.firstOrNull { it.id == id } ?: notes.second.first { it.id == id }
            }
            .distinctUntilChanged()
}