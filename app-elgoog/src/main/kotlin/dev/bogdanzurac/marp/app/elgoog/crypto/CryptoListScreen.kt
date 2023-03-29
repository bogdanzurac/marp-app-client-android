package dev.bogdanzurac.marp.app.elgoog.crypto

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.bogdanzurac.marp.app.elgoog.core.theme.ElgoogTheme
import dev.bogdanzurac.marp.app.elgoog.core.ui.composable.BaseScreen
import dev.bogdanzurac.marp.app.elgoog.core.ui.composable.EmptyView
import dev.bogdanzurac.marp.app.elgoog.core.ui.composable.LoadingView
import dev.bogdanzurac.marp.app.elgoog.crypto.CryptoListViewModel.CryptoListUiState.*
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun CryptoListScreen(viewModel: CryptoListViewModel = koinViewModel()) =
    BaseScreen(viewModel) { state ->
        when (val uiState = state.value) {
            is Loading -> LoadingView()
            is Error -> EmptyView()
            is Success ->
                if (uiState.cryptoAssets.isEmpty()) EmptyView()
                else CryptoListView(uiState.cryptoAssets, viewModel)
        }
    }

@Composable
private fun CryptoListView(
    cryptoAssets: List<CryptoAssetModel>,
    events: CryptoListUiEvents,
) {
    LazyColumn {
        items(cryptoAssets, { it.id }) { asset ->
            CryptoAssetView(asset) { events.onCryptoClicked(asset.id) }
        }
    }
}

@Composable
private fun CryptoAssetView(
    cryptoAsset: CryptoAssetModel,
    onCryptoClicked: (id: String) -> Unit
) {
    Card(modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth()
        .clickable { onCryptoClicked(cryptoAsset.id) }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = cryptoAsset.symbol,
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = cryptoAsset.priceFormatted,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = cryptoAsset.name,
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = cryptoAsset.changePercentFormatted,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Composable
@Preview
private fun CryptoListPreview() {
    ElgoogTheme {
        CryptoListView(
            cryptoAssets = MutableList(20) { composeCryptoAssetModelPreview },
            events = object : CryptoListUiEvents {
                override fun onCryptoClicked(id: String) {}
            })
    }
}