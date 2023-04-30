package dev.bogdanzurac.marp.feature.notes.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.bogdanzurac.marp.core.ui.DateTimeAttribute
import dev.bogdanzurac.marp.core.ui.composable.BaseScreen
import dev.bogdanzurac.marp.core.ui.composable.EmptyView
import dev.bogdanzurac.marp.core.ui.composable.LoadingView
import dev.bogdanzurac.marp.core.ui.format
import dev.bogdanzurac.marp.core.ui.toLocalDateTime
import dev.bogdanzurac.marp.feature.crypto.ui.common.priceFormatted
import dev.bogdanzurac.marp.feature.notes.domain.Note
import dev.bogdanzurac.marp.feature.notes.ui.NoteDetailsViewModel.NoteDetailsArgs
import dev.bogdanzurac.marp.feature.notes.ui.NoteDetailsViewModel.NoteDetailsUiState.*
import dev.bogdanzurac.marp.feature.notes.ui.common.composeNotePreview
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import dev.bogdanzurac.marp.core.ui.R as CoreUiR

@Composable
internal fun NoteDetailsScreen(
    noteId: String?,
    cryptoId: String? = null,
    viewModel: NoteDetailsViewModel = koinViewModel(parameters = {
        parametersOf(NoteDetailsArgs(noteId, cryptoId))
    })
) = BaseScreen(viewModel) { state ->
    when (val uiState = state.value) {
        is Loading -> LoadingView()
        is Error -> EmptyView()
        is Success -> NoteView(uiState.note, viewModel)
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NoteView(
    note: Note,
    events: NoteDetailsUiEvents,
) {
    Scaffold(
        floatingActionButton = {
            if (note.isEditable) {
                FloatingActionButton(
                    onClick = events::onDeleteNoteClicked,
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = stringResource(CoreUiR.string.button_delete),
                    )
                }
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                var title by remember { mutableStateOf(note.title) }
                var body by remember { mutableStateOf(note.body) }
                OutlinedTextField(
                    value = title,
                    onValueChange = { newTitle ->
                        title = newTitle
                        events.onNoteEdited(newTitle, note.body)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = note.isEditable,
                    textStyle = MaterialTheme.typography.headlineMedium,
                    label = {
                        if (note.isEditable) {
                            Text(
                                text = stringResource(R.string.label_note_title),
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                )
                note.cryptoAsset?.let { cryptoAsset ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = cryptoAsset.name,
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.surface)
                                .padding(4.dp),
                            style = MaterialTheme.typography.labelLarge,
                        )
                        Text(
                            text = cryptoAsset.priceFormatted,
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }
                }
                if (note.isEditable) {
                    Text(
                        text = note.createdAt.toLocalDateTime()
                            .format(LocalContext.current, DateTimeAttribute.DATE_TIME_SHORT),
                        modifier = Modifier.padding(top = 8.dp),
                        style = MaterialTheme.typography.labelSmall,
                    )
                }
                val focusManager = LocalFocusManager.current
                OutlinedTextField(
                    value = body,
                    onValueChange = { newBody ->
                        body = newBody
                        events.onNoteEdited(note.title, newBody)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    enabled = note.isEditable,
                    textStyle = MaterialTheme.typography.bodyLarge,
                    label = {
                        if (note.isEditable) {
                            Text(
                                text = stringResource(R.string.label_note_text),
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions { focusManager.clearFocus() },
                )
            }
        })
}

@Composable
@Preview
private fun NoteDetailsPreview() {
    MaterialTheme {
        NoteView(composeNotePreview, object : NoteDetailsUiEvents {
            override fun onDeleteNoteClicked() {}
            override fun onNoteEdited(title: String, body: String) {}
        })
    }
}