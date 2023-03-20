package dev.bogdanzurac.marp.app.elgoog.crypto

import dev.bogdanzurac.marp.app.elgoog.core.ui.BaseViewModel
import dev.bogdanzurac.marp.app.elgoog.core.arch.DialogManager
import dev.bogdanzurac.marp.app.elgoog.core.ui.UiState
import dev.bogdanzurac.marp.app.elgoog.core.foldResult
import dev.bogdanzurac.marp.app.elgoog.core.onFailure
import dev.bogdanzurac.marp.app.elgoog.core.ui.getGenericErrorDialogFor
import dev.bogdanzurac.marp.app.elgoog.crypto.CryptoDetailsViewModel.CryptoDetailsUiState
import dev.bogdanzurac.marp.app.elgoog.crypto.CryptoDetailsViewModel.CryptoDetailsUiState.*
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Factory

@Factory
internal class CryptoDetailsViewModel(
    private val dialogManager: DialogManager,
    assetId: String,
    cryptoRepository: CryptoRepository,
) : BaseViewModel<CryptoDetailsUiState>() {

    override val uiState: StateFlow<CryptoDetailsUiState> =
        cryptoRepository.observeCryptoAsset(assetId)
            .onFailure { dialogManager.showDialog(getGenericErrorDialogFor(it)) }
            .foldResult({ Success(it) }, { Error(it) })
            .asState(Loading)

    internal sealed class CryptoDetailsUiState : UiState {
        class Error(val exception: Throwable) : CryptoDetailsUiState()
        class Success(val cryptoAsset: CryptoAssetModel) : CryptoDetailsUiState()
        object Loading : CryptoDetailsUiState()
    }
}