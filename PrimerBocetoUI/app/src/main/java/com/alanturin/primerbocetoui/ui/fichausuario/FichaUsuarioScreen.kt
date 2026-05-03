package com.alanturin.primerbocetoui.ui.fichausuario

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alanturin.primerbocetoui.R
import com.alanturin.primerbocetoui.ui.alumno.ficha.FichaUsuarioViewModel

@Composable
fun FichaUsuarioScreen(
    modifier: Modifier = Modifier,
    viewModel: FichaUsuarioViewModel = hiltViewModel(),
    onJustificarClick: (Int, String, String, String, String) -> Unit,
    onEditarFicha: () -> Unit,
    onCambiarPassword: () -> Unit
) {
    LaunchedEffect(Unit) { viewModel.cargarFaltas() }

    val state by viewModel.uiState.collectAsState()

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Text(stringResource(R.string.ficha_title), style = MaterialTheme.typography.headlineMedium)

        // pruebsas de camara
        Button(
            onClick = { onJustificarClick(1,"Clase de Prueba", "2025-03-12", "08:00", "MISSING") },
            modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
        ) {
            Spacer(Modifier.width(8.dp))
            Text(stringResource(R.string.ficha_test_camera))
        }

        Button(
            onClick = onEditarFicha,
            modifier = Modifier.padding(vertical = 4.dp).fillMaxWidth()
        ) {
            Text(stringResource(R.string.ficha_btn_editar_perfil))
        }

        Button(
            onClick = onCambiarPassword,
            modifier = Modifier.padding(vertical = 4.dp).fillMaxWidth()
        ) {
            Text(stringResource(R.string.ficha_btn_cambiar_password))
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (val ui = state) {
            is FichaUsuarioViewModel.UiState.Loading -> CircularProgressIndicator()
            is FichaUsuarioViewModel.UiState.Empty -> {
                Text(text = ui.profile.name, style = MaterialTheme.typography.titleMedium)
                Text(text = ui.profile.email, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(12.dp))
                Text(stringResource(R.string.ficha_no_absences))
            }
            is FichaUsuarioViewModel.UiState.Success -> {
                Text(text = ui.profile.name, style = MaterialTheme.typography.titleMedium)
                Text(text = ui.profile.email, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(12.dp))
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(ui.faltas) { falta ->
                        FaltaCard(
                            asistencia = falta,
                            onClick = {
                                val subjectName = falta.subjectName ?: "Sin asignatura"
                                val date = falta.date ?: ""
                                val startTime = falta.startTime ?: ""
                                onJustificarClick(
                                    falta.id,
                                    subjectName,
                                    date,
                                    startTime,
                                    falta.status
                                )
                            }
                        )
                    }
                }
            }
            is FichaUsuarioViewModel.UiState.TeacherSuccess -> {
                Text(text = ui.profile.name, style = MaterialTheme.typography.titleMedium)
                Text(text = ui.profile.email, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(12.dp))
                TarjetaProfesor(teacher = ui.teacher)
            }
            is FichaUsuarioViewModel.UiState.Error -> Text("Error: ${ui.message}", color = Color.Red)

            else -> {}
        }
    }
}