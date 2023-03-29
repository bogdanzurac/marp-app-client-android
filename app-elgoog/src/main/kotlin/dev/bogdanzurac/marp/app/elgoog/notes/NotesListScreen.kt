package dev.bogdanzurac.marp.app.elgoog.notes

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.bogdanzurac.marp.app.elgoog.R
import dev.bogdanzurac.marp.app.elgoog.core.theme.ElgoogTheme
import dev.bogdanzurac.marp.app.elgoog.core.ui.composable.BaseScreen
import dev.bogdanzurac.marp.app.elgoog.core.ui.composable.EmptyView
import dev.bogdanzurac.marp.app.elgoog.core.ui.composable.LoadingView
import dev.bogdanzurac.marp.app.elgoog.notes.NotesListViewModel.NotesListUiState.*
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun NotesListScreen(viewModel: NotesListViewModel = koinViewModel()) =
    BaseScreen(viewModel) { state ->
        when (val uiState = state.value) {
            is Loading -> LoadingView()
            is Error, Empty -> EmptyView()
            is Success -> NotesListView(uiState, viewModel)
            is NotLoggedIn -> NotLoggedInView(viewModel)
        }
    }

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NotesListView(
    state: Success,
    events: NotesListUiEvents,
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = events::onAddNoteClicked,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(R.string.button_add),
                )
            }
        },
        content = {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
            ) {
                if (state.personalNotes.isNotEmpty()) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Text(
                            text = stringResource(R.string.header_personal_notes),
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(16.dp),
                        )
                    }
                    items(state.personalNotes, { it.id }) { note ->
                        NoteView(note, addPadding = true) { events.onNoteClicked(note.id) }
                    }
                }
                if (state.cryptoNotes.isNotEmpty()) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Text(
                            text = stringResource(R.string.header_crypto_notes),
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(16.dp),
                        )
                    }
                    items(state.cryptoNotes, { it.id }) { note ->
                        NoteView(note, addPadding = true) { events.onNoteClicked(note.id) }
                    }
                }
            }
        })
}

@Composable
private fun NotLoggedInView(events: NotesListUiEvents) {
    Column {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.ic_empty),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
                contentDescription = stringResource(R.string.empty),
            )
            Text(
                text = stringResource(R.string.empty),
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(16.dp)
            )
            Button(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                onClick = { events.onLoginClicked() }) {
                Text(
                    text = stringResource(R.string.button_login),
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
    }
}

@Composable
@Preview
private fun NotesListPreview() {
    ElgoogTheme {
        NotesListView(
            Success(
                personalNotes = MutableList(10) { composeNotePreview },
                cryptoNotes = MutableList(10) { composeNotePreview }),
            events = object : NotesListUiEvents {
                override fun onAddNoteClicked() {}
                override fun onNoteClicked(id: String) {}
                override fun onLoginClicked() {}
            })
    }
}