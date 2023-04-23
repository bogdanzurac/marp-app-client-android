package dev.bogdanzurac.marp.app.elgoog.notes

import dev.bogdanzurac.marp.app.elgoog.core.auth.AuthManager
import dev.bogdanzurac.marp.app.elgoog.crypto.CryptoRepository
import dev.bogdanzurac.marp.core.mapResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapConcat
import org.koin.core.annotation.Singleton

@Singleton
class ObserveUserNotesUseCase(
    private val authManager: AuthManager,
    private val cryptoRepository: CryptoRepository,
    private val notesRepository: NotesRepository,
) {

    operator fun invoke(): Flow<Result<List<Note>>> =
        authManager.observeUser()
            .filterNotNull()
            .flatMapConcat { user ->
                notesRepository.observeNotes(
                    listOf(Triple("userId", true, user.id))
                )
            }
            .mapResult { notes ->
                notes.map { note ->
                    note.cryptoId?.let {
                        return@map note.copy(
                            cryptoAsset = cryptoRepository.getCryptoAsset(it).getOrNull()
                        )
                    } ?: note
                }
            }
}