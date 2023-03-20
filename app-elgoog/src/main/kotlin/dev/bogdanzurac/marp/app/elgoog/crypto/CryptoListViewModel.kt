package dev.bogdanzurac.marp.app.elgoog.crypto

import dev.bogdanzurac.marp.app.elgoog.core.ui.BaseViewModel
import dev.bogdanzurac.marp.app.elgoog.core.arch.DialogManager
import dev.bogdanzurac.marp.app.elgoog.core.ui.UiState
import dev.bogdanzurac.marp.app.elgoog.core.foldResult
import dev.bogdanzurac.marp.app.elgoog.core.onFailure
import dev.bogdanzurac.marp.app.elgoog.core.ui.getGenericErrorDialogFor
import dev.bogdanzurac.marp.app.elgoog.crypto.CryptoListViewModel.CryptoListUiState
import dev.bogdanzurac.marp.app.elgoog.crypto.CryptoListViewModel.CryptoListUiState.*
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Factory

@Factory
internal class CryptoListViewModel(
    private val dialogManager: DialogManager,
    private val cryptoNavigator: CryptoNavigator,
    cryptoRepository: CryptoRepository,
) : BaseViewModel<CryptoListUiState>(), CryptoListUiEvents {

    override val uiState: StateFlow<CryptoListUiState> =
        cryptoRepository.observeCryptoAssets()
            .onFailure { dialogManager.showDialog(getGenericErrorDialogFor(it)) }
            .foldResult({ Success(it) }, { Error(it) })
            .asState(Loading)

    internal sealed class CryptoListUiState : UiState {
        class Error(val exception: Throwable) : CryptoListUiState()
        class Success(val cryptoAssets: List<CryptoAssetModel>) : CryptoListUiState()
        object Loading : CryptoListUiState()
    }

    override fun navigateToDetails(id: String) {
        cryptoNavigator.navigateToAssetDetails(id)
    }
}

internal interface CryptoListUiEvents {
    fun navigateToDetails(id: String)
}