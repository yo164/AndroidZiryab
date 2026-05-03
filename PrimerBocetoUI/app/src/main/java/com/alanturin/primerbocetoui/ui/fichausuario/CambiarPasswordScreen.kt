package com.alanturin.primerbocetoui.ui.fichausuario

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alanturin.primerbocetoui.R

@Composable
fun CambiarPasswordScreen(
    modifier: Modifier = Modifier,
    viewModel: CambiarPasswordViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val estado by viewModel.uiState.collectAsStateWithLifecycle()

    var actual by remember { mutableStateOf("") }
    var nueva by remember { mutableStateOf("") }
    var confirmar by remember { mutableStateOf("") }

    val campoPasswordOpts = KeyboardOptions(keyboardType = KeyboardType.Password)
    val transformacion = PasswordVisualTransformation()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextButton(onClick = onBack) {
            Text(stringResource(R.string.alumno_temario_cd_back))
        }
        Text(
            text = stringResource(R.string.cambiar_password_title),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = actual,
            onValueChange = { actual = it },
            label = { Text(stringResource(R.string.cambiar_password_actual)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = transformacion,
            keyboardOptions = campoPasswordOpts
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = nueva,
            onValueChange = { nueva = it },
            label = { Text(stringResource(R.string.cambiar_password_nueva)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = transformacion,
            keyboardOptions = campoPasswordOpts
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = confirmar,
            onValueChange = { confirmar = it },
            label = { Text(stringResource(R.string.cambiar_password_confirmar)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = transformacion,
            keyboardOptions = campoPasswordOpts
        )
        Spacer(modifier = Modifier.height(16.dp))

        when (val s = estado) {
            is CambiarPasswordViewModel.UiState.Loading -> CircularProgressIndicator()
            is CambiarPasswordViewModel.UiState.Success -> {
                Text(
                    text = stringResource(R.string.cambiar_password_success),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            is CambiarPasswordViewModel.UiState.NoMatch -> {
                Text(
                    text = stringResource(R.string.cambiar_password_no_match),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            is CambiarPasswordViewModel.UiState.Error -> {
                Text(
                    text = s.message.ifBlank { stringResource(R.string.cambiar_password_error) },
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            is CambiarPasswordViewModel.UiState.Idle -> { }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { viewModel.cambiarPassword(actual, nueva, confirmar) },
            modifier = Modifier.fillMaxWidth(),
            enabled = estado !is CambiarPasswordViewModel.UiState.Loading
        ) {
            Text(stringResource(R.string.cambiar_password_btn))
        }
    }
}
