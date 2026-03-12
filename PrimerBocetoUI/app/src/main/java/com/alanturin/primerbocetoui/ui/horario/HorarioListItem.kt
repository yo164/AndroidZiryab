package com.alanturin.primerbocetoui.ui.horario



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HorarioCard(
    dia: String,
    horaInicio: String,
    horaFin: String,
    subject: String,
    group: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = dia, style = MaterialTheme.typography.titleMedium)
                Text(text = "$horaInicio - $horaFin", style = MaterialTheme.typography.bodyMedium)
            }
            Text(text = subject, style = MaterialTheme.typography.bodyLarge)
            Text(text = group, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HorarioCardPreview() {
    HorarioCard(
        dia = "Lunes",
        horaInicio = "09:00",
        horaFin = "10:00",
        subject = "Programación",
        group = "DAM - Mañana"
    )
}