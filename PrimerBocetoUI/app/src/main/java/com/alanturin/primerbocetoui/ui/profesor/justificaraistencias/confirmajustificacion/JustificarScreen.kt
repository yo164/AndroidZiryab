package com.alanturin.primerbocetoui.ui.profesor.justificaraistencias.confirmajustificacion

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.alanturin.primerbocetoui.ui.navigation.Route

@Composable
fun Route.JustificarScreen(
    viewModel: JustificarViewModel = hiltViewModel()
) {
    val uri by viewModel.uri.collectAsState()
    val justified by viewModel.justified.collectAsState()

    val buttonColor by animateColorAsState(
        targetValue = if (justified) Color(0xFF4CAF50) else MaterialTheme.colorScheme.primary
    )

    Column(modifier = Modifier.fillMaxSize()) {
        AsyncImage(
            model = uri,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = { viewModel.justificar() }
                    )
                },
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
        ) {
            Text(if (justified) "Justificado" else "Mantén para validar")
        }
    }
}