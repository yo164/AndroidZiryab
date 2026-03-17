package com.alanturin.primerbocetoui.ui.profesor.listaAlumnos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alanturin.primerbocetoui.R

@Composable
fun AlumnoListScreen(
    modifier: Modifier = Modifier,
    viewModel: AlumnoListViewModel = hiltViewModel()
) {
    LaunchedEffect(true) {
        viewModel.cargarAlumnos()
    }

    val state by viewModel.uiState.collectAsState()
    val asistencias by viewModel.asistencias.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when (val ui = state) {
            is AlumnoListViewModel.UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is AlumnoListViewModel.UiState.Error -> {
                Text(text = ui.message, color = Color.Red)
            }
            is AlumnoListViewModel.UiState.Empty -> {
                Text(text = stringResource(R.string.alumno_list_empty))
            }
            is AlumnoListViewModel.UiState.Success -> {
                LazyColumn(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(ui.alumnos) { enrollment ->

                        val status = asistencias[enrollment.id]

                        AlumnoCard(
                            nombre = enrollment.student.name,
                            surname = enrollment.student.surname,
                            ndSurname = enrollment.student.ndSurname,
                            status = status,

                            {
                                    status ->
                                viewModel.actualizarAsistencia(enrollment.id, status)
                            }
                        )
                    }
                }
            }

        }
        Button(
            onClick = { viewModel.enviarAsistencias() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(stringResource(R.string.alumno_list_send))
        }
    }

}