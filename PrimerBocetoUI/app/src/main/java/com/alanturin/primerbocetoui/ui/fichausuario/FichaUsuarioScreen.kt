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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alanturin.primerbocetoui.ui.alumno.ficha.FichaUsuarioViewModel

@Composable
fun FichaUsuarioScreen(
    modifier: Modifier = Modifier,
    viewModel: FichaUsuarioViewModel = hiltViewModel(),
    onJustificarClick: (String, String, String, String) -> Unit
) {
    LaunchedEffect(Unit) { viewModel.cargarFaltas() }

    val state by viewModel.uiState.collectAsState()

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Text("Mis Asistencias", style = MaterialTheme.typography.headlineMedium)

        // pruebsas de camara
        Button(
            onClick = {
                onJustificarClick("CLASE DE PRUEBA", "15/03/2026", "12:00", "TEST")
            },
            modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
        ) {
            Spacer(Modifier.width(8.dp))
            Text("PROBAR CAMARA (MODO TEST)")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (val ui = state) {
            is FichaUsuarioViewModel.UiState.Loading -> CircularProgressIndicator()
            is FichaUsuarioViewModel.UiState.Empty -> {
                Text("No tienes faltas registradas en el servidor local.")
            }
            is FichaUsuarioViewModel.UiState.Success -> {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(ui.faltas) { falta ->
                        FaltaCard(
                            asistencia = falta,
                            onClick = {
                                onJustificarClick(
                                    falta.session.schedule.teacherAssignment.subject.name,
                                    falta.session.date,
                                    falta.session.schedule.startTime,
                                    falta.status
                                )
                            }
                        )
                    }
                }
            }
            is FichaUsuarioViewModel.UiState.Error -> Text("Error: ${ui.message}", color = Color.Red)
            else -> {}
        }
    }
}