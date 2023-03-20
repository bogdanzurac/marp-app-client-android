package dev.bogdanzurac.marp.app.elgoog.core.ui.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.bogdanzurac.marp.app.elgoog.core.arch.DialogManager
import org.koin.androidx.compose.get

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AppDialog(dialogManager: DialogManager) {
    dialogManager.observeDialogs()
        .collectAsStateWithLifecycle(initialValue = null).value?.let { dialogContent ->
            Dialog(
                onDismissRequest = {},
                properties = DialogProperties(usePlatformDefaultWidth = false),
                content = {
                    Card {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = dialogContent.title.getText(),
                                style = MaterialTheme.typography.headlineSmall,
                            )
                            Spacer(Modifier.size(8.dp))
                            Text(
                                text = dialogContent.body.getText(),
                                style = MaterialTheme.typography.bodyMedium,
                            )
                            Button(
                                modifier = Modifier
                                    .padding(top = 16.dp)
                                    .fillMaxWidth(),
                                onClick = { dialogManager.sendEvent() }) {
                                Text(
                                    text = dialogContent.button.getText(),
                                    style = MaterialTheme.typography.labelLarge,
                                )
                            }
                        }
                    }
                })
        }
}

@Composable
@Preview
private fun AppDialogPreview() {
    AppDialog(get())
}