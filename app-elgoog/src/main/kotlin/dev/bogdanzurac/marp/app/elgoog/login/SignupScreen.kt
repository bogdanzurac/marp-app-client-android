package dev.bogdanzurac.marp.app.elgoog.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.bogdanzurac.marp.app.elgoog.R
import dev.bogdanzurac.marp.app.elgoog.core.theme.ElgoogTheme
import dev.bogdanzurac.marp.core.ui.composable.BaseScreen
import dev.bogdanzurac.marp.core.ui.composable.LoadingView
import dev.bogdanzurac.marp.app.elgoog.login.SignupViewModel.SignupUiState.*
import org.koin.androidx.compose.koinViewModel
import java.util.*

@Composable
internal fun SignupScreen(viewModel: SignupViewModel = koinViewModel()) =
    BaseScreen(viewModel) { state ->
        when (state.value) {
            is Loading -> LoadingView()
            is SignUp -> SignupView(viewModel)
        }
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SignupView(events: SignupUiEvents) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        OutlinedTextField(
            value = email,
            onValueChange = { email = it.trim() },
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyLarge,
            label = {
                Text(
                    text = stringResource(R.string.label_email),
                    style = MaterialTheme.typography.labelMedium
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
        )
        val focusManager = LocalFocusManager.current
        OutlinedTextField(
            value = password,
            onValueChange = { password = it.trim() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            textStyle = MaterialTheme.typography.bodyLarge,
            label = {
                Text(
                    text = stringResource(R.string.label_password),
                    style = MaterialTheme.typography.labelMedium
                )
            },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions { focusManager.clearFocus() },
        )
        Button(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            onClick = { events.onSignupClicked(email, password) }) {
            Text(
                text = stringResource(R.string.button_signup),
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}

@Composable
@Preview
private fun SignupPreview() {
    ElgoogTheme {
        SignupView(object : SignupUiEvents {
            override fun onSignupClicked(email: String, password: String) {}
        })
    }
}