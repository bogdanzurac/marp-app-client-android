package dev.bogdanzurac.marp.feature.notes.domain

import dev.bogdanzurac.marp.core.combineResult
import dev.bogdanzurac.marp.core.mapResultCatching
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