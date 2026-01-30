package com.alanturin.primerbocetoui.ui.alumno

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
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
    modifier: Modifier = Modifier,
    viewModel: ClasesAlumnoViewModel = hiltViewModel(),
    onAsignaturaClick: (Long, String) -> Unit
) {
    val asignaturas by viewModel.asignaturas.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(true) {
        viewModel.cargarClases(2L)
    }

    Scaffold(
        containerColor = Color(0xFFF8FAFC)
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Spacer(modifier = Modifier.height(60.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Mis Asignaturas",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            brush = Brush.horizontalGradient(
                                colors = listOf(Color(0xFF7C3AED), Color(0xFF4F46E5))
                            )
                        )
                    )

                    IconButton(
                        onClick = { viewModel.recargarClases(2L) },
                        enabled = !isLoading
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                strokeWidth = 2.dp,
                                color = Color(0xFF7C3AED)
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Recargar",
                                tint = Color(0xFF7C3AED)
                            )
                        }
                    }
                }

                if (isLoading && asignaturas.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Color(0xFF7C3AED))
                    }
                } else if (error != null && asignaturas.isEmpty()) {
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
                    // LISTA DE ASIGNATURAS
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        itemsIndexed(asignaturas) { index, asignatura ->
                            AsignaturaCard(
                                asignatura = asignatura,
                                index = index,
                                onClick = onAsignaturaClick
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AsignaturaCard(
    asignatura: Asignatura,
    index: Int,
    onClick: (Long, String) -> Unit
) {
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
            .padding(vertical = 4.dp)
            .clickable { onClick(asignatura.id, asignatura.nombre) },
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
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = asignatura.nombre,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = (-0.5).sp
                    ),
                    color = Color(0xFF1F2937),
                    textAlign = TextAlign.Center
                )

                HorizontalDivider(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth(0.8f),
                    thickness = 2.dp,
                    color = borderColor.copy(alpha = 0.6f)
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "GRADO/CURSO",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        ),
                        color = Color(0xFF6B7280),
                        modifier = Modifier.padding(bottom = 6.dp)
                    )

                    Surface(
                        shape = RoundedCornerShape(50),
                        color = Color.White.copy(alpha = 0.6f),
                        shadowElevation = 0.dp,
                        border = null
                    ) {
                        Text(
                            text = asignatura.curso,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Medium
                            ),
                            color = Color(0xFF374151),
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { onClick(asignatura.id, asignatura.nombre) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = textColor
                    ),
                    shape = RoundedCornerShape(16.dp),
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