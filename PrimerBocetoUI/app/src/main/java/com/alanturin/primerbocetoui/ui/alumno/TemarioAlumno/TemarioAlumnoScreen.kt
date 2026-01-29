package com.alanturin.primerbocetoui.ui.alumno.TemarioAlumno

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

data class UnidadFake(
    val id: Int,
    val titulo: String,
    val temas: List<String>,
    var isExpanded: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemarioAlumnoScreen(
    asignaturaId: Long,
    asignaturaNombre: String,
    userRole: String, // Para saber si mostramos botón de subir tarea
    onBack: () -> Unit
) {
    // Generamos datos falsos según la asignatura pulsada
    var unidades by remember {
        mutableStateOf(
            listOf(
                UnidadFake(1, "Introducción a $asignaturaNombre", listOf("Tema 1: Conceptos", "Tema 2: Instalación")),
                UnidadFake(2, "Bloque Principal", listOf("Tema 3: Práctica 1", "Tema 4: Teoría Avanzada")),
                UnidadFake(3, "Examen y Entrega", listOf("Proyecto Final", "Revisión"))
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(asignaturaNombre, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        // Si es profesor, mostramos botón flotante
        floatingActionButton = {
            if (userRole == "TEACHER") {
                FloatingActionButton(onClick = { /* Subir Tarea */ }, containerColor = Color(0xFF7C3AED)) {
                    Icon(Icons.Default.Add, contentDescription = "Añadir", tint = Color.White)
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(unidades) { index, unidad ->
                // Colores pastel alternos (imitando tu Angular)
                val bgColor = when (index % 4) {
                    0 -> Color(0xFFEFF6FF) // Azul
                    1 -> Color(0xFFFDF4FF) // Rosa
                    2 -> Color(0xFFECFDF5) // Verde
                    else -> Color(0xFFFFFBEB) // Amarillo
                }

                Card(
                    modifier = Modifier.fillMaxWidth().animateContentSize(),
                    colors = CardDefaults.cardColors(containerColor = bgColor),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column {
                        // CABECERA CLICKABLE
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    // Lógica acordeón
                                    unidades = unidades.map {
                                        if (it.id == unidad.id) it.copy(isExpanded = !it.isExpanded)
                                        else it // Pon 'it.copy(isExpanded = false)' si quieres cerrar las otras
                                    }
                                }
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier.size(40.dp).clip(RoundedCornerShape(8.dp))
                                        .background(if (unidad.isExpanded) Color(0xFF7C3AED) else Color.White),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = unidad.id.toString(),
                                        fontWeight = FontWeight.Bold,
                                        color = if (unidad.isExpanded) Color.White else Color.Gray
                                    )
                                }
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(unidad.titulo, fontWeight = FontWeight.Bold, color = Color(0xFF374151))
                            }
                            Icon(
                                if (unidad.isExpanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                tint = Color(0xFF6B7280)
                            )
                        }

                        // CONTENIDO DESPLEGABLE
                        if (unidad.isExpanded) {
                            Column(modifier = Modifier.padding(start = 24.dp, end = 16.dp, bottom = 16.dp)) {
                                unidad.temas.forEach { tema ->
                                    Card(
                                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp).clickable { /* Descargar */ },
                                        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.6f))
                                    ) {
                                        Text(text = tema, modifier = Modifier.padding(12.dp), color = Color(0xFF4B5563))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}