package com.alanturin.primerbocetoui.ui.profesor.justificaraistencias


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun NotificationListScreen(
    viewModel: NotificationListViewModel = hiltViewModel(),
    onNotificationClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is NotificationListViewModel.UiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is NotificationListViewModel.UiState.Empty -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "No hay notificaciones pendientes")
            }
        }
        is NotificationListViewModel.UiState.Success -> {
            val lista = (uiState as NotificationListViewModel.UiState.Success).notificaciones
            LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                item {
                    Text(
                        text = "Notificaciones",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                }
                items(lista) { asistencia ->
                    NotificationListItem(
                        datosAsistencia = asistencia,
                        onClick = {
                            viewModel.seleccionarNotificacion(asistencia)
                            onNotificationClick()
                        }
                    )
                }
            }
        }
        is NotificationListViewModel.UiState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = (uiState as NotificationListViewModel.UiState.Error).message)
            }
        }
    }
}