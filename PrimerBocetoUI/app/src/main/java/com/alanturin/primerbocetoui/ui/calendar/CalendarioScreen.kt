package com.alanturin.primerbocetoui.ui.calendar

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

data class EventoDummy(val dia: String, val mes: String, val titulo: String, val tipo: String)

@Composable
fun CalendarioScreen(modifier: Modifier = Modifier) {

    // Datos Falsos
    val eventos = listOf(
        EventoDummy("12", "FEB", "Examen Programacion", "Examen"),
        EventoDummy("14", "FEB", "Entrega Proyecto intermoludar", "Entrega"),
        EventoDummy("28", "FEB", "Día de Andalucía", "Festivo"),
        EventoDummy("05", "MAR", "Excursión a charla", "Salida"),
        EventoDummy("15", "MAR", "Examen Base de Datos", "Examen")
    )

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Próximos Eventos",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF4C1D95),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(eventos) { evento ->
                EventoItem(evento)
            }
        }
    }
}

@Composable
fun EventoItem(evento: EventoDummy) {
    val colorBorde = when (evento.tipo) {
        "Examen" -> Color(0xFFEF4444)
        "Festivo" -> Color(0xFF10B981)
        else -> Color(0xFF7C3AED)
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .width(50.dp)
            ) {
                Text(
                    text = evento.dia,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = colorBorde
                )
                Text(
                    text = evento.mes,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
            }

            VerticalDivider(
                modifier = Modifier
                    .height(40.dp)
                    .padding(horizontal = 12.dp),
                thickness = 2.dp,
                color = Color.LightGray.copy(alpha = 0.5f)
            )

            Column {
                Text(
                    text = evento.titulo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = evento.tipo,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }
}