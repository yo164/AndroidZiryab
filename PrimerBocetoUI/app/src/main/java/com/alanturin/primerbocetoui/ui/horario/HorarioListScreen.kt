package com.alanturin.primerbocetoui.ui.horario

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HorarioScreen(
    modifier: Modifier = Modifier,
    viewModel: HorarioListViewModel = hiltViewModel()
) {
    LaunchedEffect(true) {
        viewModel.cargarHorario()
    }

    val state by viewModel.uiState.collectAsState()
    var diaSeleccionado by remember { mutableStateOf(WeekDay.LUNES) }

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            WeekDay.entries.forEach { dia ->
                Button(
                    onClick = { diaSeleccionado = dia },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (diaSeleccionado == dia) Color(0xFF7C3AED) else Color.LightGray
                    )
                ) {
                    Text(dia.abreviatura)
                }
            }
        }

        when (val ui = state) {
            is HorarioListViewModel.UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is HorarioListViewModel.UiState.Error -> {
                Text(text = ui.message, color = Color.Red)
            }
            is HorarioListViewModel.UiState.Empty -> {
                Text(text = "No hay horario disponible.")
            }
            is HorarioListViewModel.UiState.Success -> {
                val horariosFiltrados = ui.horarios.filter { it.weekDay == diaSeleccionado.numero }
                LazyColumn(
                    modifier = Modifier.padding(top = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(horariosFiltrados) { horario ->
                        HorarioCard(
                            dia = diaSeleccionado.name,
                            horaInicio = horario.startTime,
                            horaFin = horario.finishTime,
                            subject = horario.subjectName,
                            group = horario.groupName
                        )
                    }
                }
            }
        }
    }
}