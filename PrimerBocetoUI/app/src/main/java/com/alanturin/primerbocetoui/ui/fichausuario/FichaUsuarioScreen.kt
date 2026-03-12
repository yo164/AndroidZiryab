package com.alanturin.primerbocetoui.ui.fichausuario

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alanturin.primerbocetoui.ui.alumno.ficha.FichaUsuarioViewModel

@Composable
fun FichaUsuarioScreen(
    modifier: Modifier = Modifier,
    viewModel: FichaUsuarioViewModel = hiltViewModel()
) {
    LaunchedEffect(true) {
        viewModel.cargarFaltas()
    }

    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when (val ui = state) {
            is FichaUsuarioViewModel.UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is FichaUsuarioViewModel.UiState.Error -> {
                Text(text = ui.message, color = Color.Red)
            }
            is FichaUsuarioViewModel.UiState.Empty -> {
                Text(text = "No tienes faltas registradas.")
            }
            is FichaUsuarioViewModel.UiState.Success -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(ui.faltas) { falta ->
                        FaltaCard(
                            asistencia = falta,
                            onClick = { }
                        )
                    }
                }
            }
        }
    }
}