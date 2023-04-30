package dev.bogdanzurac.marp.feature.notes.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.bogdanzurac.marp.feature.notes.domain.Note

@Composable
fun NoteView(
    note: Note,
    addPadding: Boolean = false,
    onNoteClicked: (id: String) -> Unit,
) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(if (addPadding) 16.dp else 0.dp)
        .clickable { onNoteClicked(note.id) }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.bodyLarge,
            )
            note.cryptoAsset?.let { cryptoAsset ->
                Text(
                    text = cryptoAsset.name,
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(4.dp),
                    style = MaterialTheme.typography.labelMedium,
                )
            }
            Text(
                text = note.body,
                modifier = Modifier.padding(top = 8.dp),
                style = MaterialTheme.typography.bodySmall,
                maxLines = 5,
            )
        }
    }
}

@Composable
@Preview
private fun NotePreview() {
    MaterialTheme {
        NoteView(note = composeNotePreview, onNoteClicked = { })
    }
}
