package com.alanturin.primerbocetoui.ui.asistencias

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ListaAlumnos(){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .then(height?.let { Modifier.height(it) } ?: Modifier) // Si height es null, usa altura automática
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp), // espacio entre los textos
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Nombre Profesor",
                style = MaterialTheme.typography.bodyMedium, // un poco más pequeño que el título
                color = MaterialTheme.colorScheme.onSurfaceVariant // opcional, para diferenciarlo
            )
        }
    }
}
}