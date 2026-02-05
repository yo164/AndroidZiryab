package com.alanturin.primerbocetoui.ui.noticeBoard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun TablonScreen(modifier: Modifier = Modifier) {

    val notificaciones = listOf(
        Pair("Urgente: Cambio de aula", "La clase de Matemáticas de hoy será en el Aula 3."),
        Pair("Becas MEC", "Se ha abierto el plazo para solicitar las becas del ministerio."),
        Pair("Parking", "Las plazas de parking van a estar cerradas hasta nuevo aviso."),
        Pair("Huelga Transporte", "Posibles retrasos en las rutas de autobús mañana.")
    )

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Tablón de Anuncios",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4C1D95),
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        items(notificaciones) { (titulo, cuerpo) ->
            NotificacionItem(titulo, cuerpo)
        }
    }
}

@Composable
fun NotificacionItem(titulo: String, cuerpo: String) {
    val isUrgent = titulo.contains("Urgente")
    val iconColor = if (isUrgent) Color(0xFFEF4444) else Color(0xFF7C3AED)
    val icon = if (isUrgent) Icons.Default.Warning else Icons.Default.Info

    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = titulo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F2937)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = cuerpo,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF4B5563)
                )
            }
        }
    }
}