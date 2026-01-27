package com.alanturin.primerbocetoui.ui.alumno

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alanturin.primerbocetoui.data.remote.model.Asignatura

@Composable
fun ClasesAlumnoScreen(
    viewModel: ClasesAlumnoViewModel = hiltViewModel()
) {
    val asignaturas by viewModel.asignaturas.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    // Cargar datos al iniciar
    LaunchedEffect(true) {
        viewModel.cargarClases(2L) // ID del alumno (puedes cambiarlo dinámicamente luego)
    }

    Scaffold(
        containerColor = Color(0xFFF8FAFC) // Fondo gris muy claro (Slate-50)
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // CABECERA
            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Mis Asignaturas",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        brush = Brush.horizontalGradient(
                            colors = listOf(Color(0xFF7C3AED), Color(0xFF4F46E5)) // Violet to Indigo
                        )
                    ),
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                if (isLoading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Color(0xFF7C3AED))
                    }
                } else if (error != null) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF2F2)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = error ?: "Error desconocido",
                            color = Color.Red,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                } else {
                    // LISTA DE TARJETAS
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        itemsIndexed(asignaturas) { index, asignatura ->
                            AsignaturaCard(asignatura = asignatura, index = index)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AsignaturaCard(asignatura: Asignatura, index: Int) {
    // TEMAS DE COLOR (Igual que en tu Angular)
    val themes = listOf(
        listOf(Color(0xFFDBEAFE), Color(0xFFEFF6FF)), // Blue
        listOf(Color(0xFFF3E8FF), Color(0xFFFAF5FF)), // Purple
        listOf(Color(0xFFD1FAE5), Color(0xFFECFDF5)), // Emerald
        listOf(Color(0xFFFFE4E6), Color(0xFFFFF1F2)), // Rose
        listOf(Color(0xFFFEF3C7), Color(0xFFFFFBEB)), // Amber
        listOf(Color(0xFFCFFAFE), Color(0xFFECFEFF))  // Cyan
    )

    val borderColors = listOf(
        Color(0xFFBFDBFE), Color(0xFFD8B4FE), Color(0xFF6EE7B7),
        Color(0xFFFDA4AF), Color(0xFFFDE68A), Color(0xFF67E8F9)
    )

    val textColors = listOf(
        Color(0xFF2563EB), Color(0xFF9333EA), Color(0xFF059669),
        Color(0xFFE11D48), Color(0xFFD97706), Color(0xFF0891B2)
    )

    val currentThemeIndex = index % themes.size
    val gradientColors = themes[currentThemeIndex]
    val borderColor = borderColors[currentThemeIndex]
    val textColor = textColors[currentThemeIndex]

    Card(
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp) // Un poco de aire vertical
            .clickable { /* Navegar */ },
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = gradientColors
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp) // Padding generoso como en la web (p-6)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 1. TÍTULO ASIGNATURA
                Text(
                    text = asignatura.nombre,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = (-0.5).sp
                    ),
                    color = Color(0xFF1F2937), // Gray-800
                    textAlign = TextAlign.Center
                )

                // 2. LÍNEA DIVISORIA (hr)
                HorizontalDivider(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth(0.8f), // Que no llegue hasta los bordes
                    thickness = 2.dp,
                    color = borderColor.copy(alpha = 0.6f)
                )

                // 3. SECCIÓN GRADO/CURSO (Sin iconos, solo texto y pastilla)
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Etiqueta pequeña "GRADO/CURSO"
                    Text(
                        text = "GRADO/CURSO",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        ),
                        color = Color(0xFF6B7280), // Gray-500
                        modifier = Modifier.padding(bottom = 6.dp)
                    )

                    // La "Pastilla" blanca (bg-white/60)
                    Surface(
                        shape = RoundedCornerShape(50), // Completamente redonda
                        color = Color.White.copy(alpha = 0.6f),
                        shadowElevation = 0.dp,
                        border = null
                    ) {
                        Text(
                            text = asignatura.curso, // "1º ESO - A"
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Medium
                            ),
                            color = Color(0xFF374151), // Gray-700
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 4. BOTÓN ACCEDER
                Button(
                    onClick = { /* Acción */ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = textColor
                    ),
                    shape = RoundedCornerShape(16.dp), // rounded-xl
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 2.dp,
                        pressedElevation = 0.dp
                    )
                ) {
                    Text(
                        text = "Acceder",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}