package dev.bogdanzurac.marp.app.elgoog.crypto

import dev.bogdanzurac.marp.core.prompts.DialogManager
import dev.bogdanzurac.marp.core.ui.BaseViewModel
import dev.bogdanzurac.marp.core.ui.Tracker
import dev.bogdanzurac.marp.core.ui.UiState
import dev.bogdanzurac.marp.core.ui.getGenericErrorDialogFor
import dev.bogdanzurac.marp.app.elgoog.crypto.CryptoListViewModel.CryptoListUiState
import dev.bogdanzurac.marp.app.elgoog.crypto.CryptoListViewModel.CryptoListUiState.*
import dev.bogdanzurac.marp.core.foldResult
import dev.bogdanzurac.marp.core.logger
import dev.bogdanzurac.marp.core.onFailure
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import org.koin.core.annotation.Factory

@Factory
internal class CryptoListViewModel(
    private val dialogManager: DialogManager,
    private val cryptoNavigator: CryptoNavigator,
    private val tracker: Tracker,
    cryptoRepository: CryptoRepository,
) : BaseViewModel<CryptoListUiState>(), CryptoListUiEvents {

    override val uiState: StateFlow<CryptoListUiState> =
        cryptoRepository.observeCryptoAssets()
            .onStart { tracker.trackScreen(CRYPTO_LIST_SCREEN) }
            .onFailure {
                logger.e("Could not get crypto list", it)
                dialogManager.showDialog(getGenericErrorDialogFor(it))
            }
            .foldResult({ Success(it) }, { Error(it) })
            .asState(Loading)

    internal sealed class CryptoListUiState : UiState {
        class Error(val exception: Throwable) : CryptoListUiState()
        class Success(val cryptoAssets: List<CryptoAssetModel>) : CryptoListUiState()
        object Loading : CryptoListUiState()
    }

    override fun onCryptoClicked(id: String) {
        cryptoNavigator.navigateToCryptoDetails(id)
    }
}

internal interface CryptoListUiEvents {
    fun onCryptoClicked(id: String)
}