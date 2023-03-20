package dev.bogdanzurac.marp.app.elgoog.crypto

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.bogdanzurac.marp.app.elgoog.R
import dev.bogdanzurac.marp.app.elgoog.core.theme.ElgoogTheme
import dev.bogdanzurac.marp.app.elgoog.core.ui.composable.BaseScreen
import dev.bogdanzurac.marp.app.elgoog.core.ui.composable.EmptyView
import dev.bogdanzurac.marp.app.elgoog.core.ui.composable.LoadingView
import dev.bogdanzurac.marp.app.elgoog.crypto.CryptoDetailsViewModel.CryptoDetailsUiState.*
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
internal fun CryptoDetailsScreen(
    assetId: String,
    viewModel: CryptoDetailsViewModel = koinViewModel(parameters = { parametersOf(assetId) })
) = BaseScreen(viewModel) { state ->
    when (val uiState = state.value) {
        is Loading -> LoadingView()
        is Error -> EmptyView()
        is Success -> CryptoDetailsView(uiState.cryptoAsset)
    }
}

@Composable
private fun CryptoDetailsView(cryptoAsset: CryptoAssetModel) {
    Column(
        Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = cryptoAsset.symbol,
                style = MaterialTheme.typography.headlineLarge,
            )
            Text(
                text = cryptoAsset.priceFormatted,
                style = MaterialTheme.typography.headlineMedium
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = cryptoAsset.name,
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                text = cryptoAsset.changePercentFormatted,
                style = MaterialTheme.typography.headlineMedium
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.crypto_market_cap),
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                text = cryptoAsset.marketCapFormatted,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.crypto_circulating_supply),
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                text = cryptoAsset.supplyFormatted,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
        cryptoAsset.maxSupply?.let {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.crypto_max_supply),
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = cryptoAsset.maxSupplyFormatted!!,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }
}

@Composable
@Preview
private fun CryptoDetailsPreview() {
    ElgoogTheme {
        CryptoDetailsView(composeCryptoAssetModelPreview)
    }
}