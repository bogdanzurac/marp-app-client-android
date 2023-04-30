package dev.bogdanzurac.marp.feature.crypto.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.bogdanzurac.marp.core.ui.composable.BaseScreen
import dev.bogdanzurac.marp.core.ui.composable.EmptyView
import dev.bogdanzurac.marp.core.ui.composable.LoadingView
import dev.bogdanzurac.marp.feature.crypto.ui.*
import dev.bogdanzurac.marp.feature.crypto.ui.CryptoDetailsViewModel.CryptoDetailsUiState.*
import dev.bogdanzurac.marp.feature.crypto.ui.common.priceFormatted
import dev.bogdanzurac.marp.feature.notes.ui.common.NoteView
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import dev.bogdanzurac.marp.core.ui.R as CoreUiR
import dev.bogdanzurac.marp.feature.notes.ui.common.R as NotesUiCommonR

@Composable
internal fun CryptoDetailsScreen(
    assetId: String,
    viewModel: CryptoDetailsViewModel = koinViewModel(parameters = { parametersOf(assetId) })
) = BaseScreen(viewModel) { state ->
    when (val uiState = state.value) {
        is Loading -> LoadingView()
        is Error -> EmptyView()
        is Success -> CryptoDetailsView(uiState, viewModel)
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CryptoDetailsView(
    state: Success,
    events: CryptoDetailsUiEvents,
) {
    Scaffold(
        floatingActionButton = {
            if (state.isAddNoteButtonVisible) {
                FloatingActionButton(
                    onClick = events::onAddNoteClicked,
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(CoreUiR.string.button_add),
                    )
                }
            }
        },
        content = {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            ) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = state.cryptoAsset.symbol,
                                style = MaterialTheme.typography.headlineLarge,
                            )
                            Text(
                                text = state.cryptoAsset.priceFormatted,
                                style = MaterialTheme.typography.headlineMedium
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = state.cryptoAsset.name,
                                style = MaterialTheme.typography.headlineLarge
                            )
                            Text(
                                text = state.cryptoAsset.changePercentFormatted,
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
                                text = state.cryptoAsset.marketCapFormatted,
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
                                text = state.cryptoAsset.supplyFormatted,
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }
                        state.cryptoAsset.maxSupply?.let {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = stringResource(R.string.crypto_max_supply),
                                    style = MaterialTheme.typography.bodyLarge,
                                )
                                Text(
                                    text = state.cryptoAsset.maxSupplyFormatted!!,
                                    style = MaterialTheme.typography.bodyLarge,
                                )
                            }
                        }
                    }
                }
                if (state.notes.isNotEmpty()) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Text(
                            text = stringResource(NotesUiCommonR.string.header_crypto_notes),
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(vertical = 16.dp),
                        )
                    }
                    items(state.notes, { it.id }) { note ->
                        NoteView(note) { events.onNoteClicked(note.id) }
                    }
                }
            }
        })
}

@Composable
@Preview
private fun CryptoDetailsPreview() {
    MaterialTheme {
        CryptoDetailsView(
            state = Success(composeCryptoAssetModelPreview),
            events = object : CryptoDetailsUiEvents {
                override fun onAddNoteClicked() {}
                override fun onNoteClicked(id: String) {}
            })
    }
}