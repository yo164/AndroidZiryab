package com.alanturin.primerbocetoui.ui.fichausuario

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alanturin.primerbocetoui.R

@Composable
fun EditarFichaScreen(
    modifier: Modifier = Modifier,
    viewModel: EditarFichaViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val perfil by viewModel.perfil.collectAsStateWithLifecycle()
    val guardarEstado by viewModel.uiState.collectAsStateWithLifecycle()

    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    LaunchedEffect(perfil) {
        perfil?.let {
            nombre = it.name
            email = it.email
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextButton(onClick = onBack) {
            Text(stringResource(R.string.alumno_temario_cd_back))
        }
        Text(
            text = stringResource(R.string.editar_ficha_title),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (perfil == null) {
            CircularProgressIndicator()
        } else {
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text(stringResource(R.string.editar_ficha_label_nombre)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(stringResource(R.string.editar_ficha_label_email)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))

            when (val s = guardarEstado) {
                is EditarFichaViewModel.UiState.Loading -> CircularProgressIndicator()
                is EditarFichaViewModel.UiState.Success -> {
                    Text(
                        text = stringResource(R.string.editar_ficha_success),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                is EditarFichaViewModel.UiState.Error -> {
                    Text(
                        text = s.message.ifBlank { stringResource(R.string.editar_ficha_error) },
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                is EditarFichaViewModel.UiState.Idle -> { }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { viewModel.guardarPerfil(nombre, email) },
                modifier = Modifier.fillMaxWidth(),
                enabled = guardarEstado !is EditarFichaViewModel.UiState.Loading
            ) {
                Text(stringResource(R.string.editar_ficha_btn_guardar))
            }
        }
    }
}
