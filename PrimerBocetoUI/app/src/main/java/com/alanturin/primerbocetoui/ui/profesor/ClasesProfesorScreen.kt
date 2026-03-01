package com.alanturin.primerbocetoui.ui.profesor

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alanturin.primerbocetoui.domain.model.Asignatura

@Composable
fun ClasesProfesorScreen(
    onGestionar: () -> Unit,
    viewModel: ClasesProfesorViewModel = hiltViewModel()
) {
    // ID quemado por ahora, igual que en tu prueba. Debería venir del Auth.
    LaunchedEffect(true) {
        viewModel.cargarClases()
    }

    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFF8FAFC)) // bg-slate-50
    ) {
        // Título H1
        Text(
            text = "Asignaturas",
            style = MaterialTheme.typography.headlineLarge,
            color = Color(0xFF7C3AED), // violet-600 approx
            modifier = Modifier.padding(bottom = 24.dp)
        )

        when (val ui = state) {
            is ClasesProfesorViewModel.UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFF8B5CF6)) // violet-500
                }
            }
            is ClasesProfesorViewModel.UiState.Error -> {
                Text(text = ui.message, color = Color.Red)
            }
            is ClasesProfesorViewModel.UiState.Empty -> {
                Text(text = "No tienes asignaturas asignadas.")
            }
            is ClasesProfesorViewModel.UiState.Success -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(ui.asignaturas) { asignatura ->
                        AsignaturaCard(
                            asignatura = asignatura,
                            onGestionar = {
                                viewModel.seleccionarAsignatura(asignatura)
                                onGestionar()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AsignaturaCard(asignatura: Asignatura, onGestionar: () -> Unit) {
    // Replicando tu tarjeta de Angular con gradiente y sombra
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clickable { /* Acción goToTemario */ },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        // Simulación de los temas de colores que tenías en Angular
        // Aquí uso uno fijo por simplicidad, pero podrías rotarlos
        val gradient = Brush.linearGradient(
            colors = listOf(Color(0xFFDBEAFE), Color(0xFFEFF6FF)) // blue-100 to blue-50
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Nombre Asignatura
            Text(
                text = asignatura.nombre,
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFF1F2937) // gray-800
            )

            HorizontalDivider(color = Color(0xFF93C5FD)) // border-blue-300

            // Grado/Curso
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "GRADO/CURSO",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
                Surface(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = Color.White.copy(alpha = 0.5f),
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Text(
                        text = asignatura.curso,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            // Botón Gestionar
            Button(
                onClick = {
                    onGestionar()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B82F6)) // blue-500
            ) {
                Text("Gestionar")
            }
        }
    }
}