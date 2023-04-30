package dev.bogdanzurac.marp.feature.crypto.ui

import dev.bogdanzurac.marp.core.*
import dev.bogdanzurac.marp.core.auth.AuthManager
import dev.bogdanzurac.marp.core.prompts.DialogManager
import dev.bogdanzurac.marp.core.ui.BaseViewModel
import dev.bogdanzurac.marp.core.ui.Tracker
import dev.bogdanzurac.marp.core.ui.UiState
import dev.bogdanzurac.marp.core.ui.getGenericErrorDialogFor
import dev.bogdanzurac.marp.feature.crypto.domain.CryptoAsset
import dev.bogdanzurac.marp.feature.crypto.domain.CryptoRepository
import dev.bogdanzurac.marp.feature.crypto.ui.CryptoDetailsViewModel.CryptoDetailsUiState
import dev.bogdanzurac.marp.feature.crypto.ui.CryptoDetailsViewModel.CryptoDetailsUiState.*
import dev.bogdanzurac.marp.feature.notes.domain.Note
import dev.bogdanzurac.marp.feature.notes.domain.ObserveCryptoAssetNotesUseCase
import kotlinx.coroutines.flow.*
import org.koin.core.annotation.Factory
import kotlin.Result.Companion.success

@Factory
internal class CryptoDetailsViewModel(
    private val assetId: String,
    private val dialogManager: DialogManager,
    private val navigator: CryptoNavigator,
    private val tracker: Tracker,
    private val authManager: AuthManager,
    private val observeCryptoAssetNotesUseCase: ObserveCryptoAssetNotesUseCase,
    cryptoRepository: CryptoRepository,
) : BaseViewModel<CryptoDetailsUiState>(), CryptoDetailsUiEvents {

    private fun observeCryptoAssetNotes(): Flow<Result<List<Note>>> =
        authManager.observeAuthenticatedState()
            .flatMapLatest { isAuthenticated ->
                if (!isAuthenticated) flowOf(success(emptyList()))
                else observeCryptoAssetNotesUseCase(assetId)
            }

    override val uiState: StateFlow<CryptoDetailsUiState> =
        cryptoRepository.observeCryptoAsset(assetId)
            .combineResult(
                observeCryptoAssetNotes(),
                authManager.observeAuthenticatedState().mapAsResult(),
            )
            .onStart { tracker.trackScreen(CRYPTO_DETAILS_SCREEN, assetId) }
            .onFailure {
                logger.e("Could not get crypto details", it)
                dialogManager.showDialog(getGenericErrorDialogFor(it))
            }
            .foldResult({ (cryptoAsset, notes, isAuthenticated) ->
                Success(cryptoAsset, notes, isAuthenticated)
            }, { Error(it) })
            .asState(Loading)

    internal sealed class CryptoDetailsUiState : UiState {
        class Error(val exception: Throwable) : CryptoDetailsUiState()
        class Success(
            val cryptoAsset: CryptoAsset,
            val notes: List<Note> = emptyList(),
            val isAddNoteButtonVisible: Boolean = true,
        ) : CryptoDetailsUiState()

        object Loading : CryptoDetailsUiState()
    }

    override fun onAddNoteClicked() {
        navigator.navigateToAddNote(assetId)
    }

    override fun onNoteClicked(id: String) {
        navigator.navigateToNoteDetails(id)
    }
}

internal interface CryptoDetailsUiEvents {
    fun onAddNoteClicked()
    fun onNoteClicked(id: String)
}