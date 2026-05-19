package com.alanturin.primerbocetoui.ui.profesor.calificarEntrega

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alanturin.primerbocetoui.R
import com.alanturin.primerbocetoui.data.remote.model.StudentTaskItemRemote

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalificarEntregaScreen(
    onBack: () -> Unit,
    viewModel: CalificarEntregaViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var entregaSnapshot by remember { mutableStateOf<StudentTaskItemRemote?>(null) }

    LaunchedEffect(uiState) {
        when (val state = uiState) {
            is CalificarEntregaUiState.Listo -> entregaSnapshot = state.entrega
            is CalificarEntregaUiState.Guardado -> onBack()
            else -> Unit
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.grade_title),
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.grade_cd_back)
                        )
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (val state = uiState) {
                is CalificarEntregaUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is CalificarEntregaUiState.Error -> {
                    ErrorBlock(
                        mensaje = state.mensaje,
                        onReintentar = { viewModel.cargar() },
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is CalificarEntregaUiState.Listo -> {
                    CalificarContent(
                        entrega = state.entrega,
                        guardando = false,
                        onGuardar = { score, feedback ->
                            viewModel.calificar(score, feedback)
                        }
                    )
                }
                is CalificarEntregaUiState.Guardando -> {
                    entregaSnapshot?.let { entrega ->
                        CalificarContent(
                            entrega = entrega,
                            guardando = true,
                            onGuardar = { _, _ -> }
                        )
                    } ?: CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is CalificarEntregaUiState.Guardado -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}

@Composable
private fun CalificarContent(
    entrega: StudentTaskItemRemote,
    guardando: Boolean,
    onGuardar: (score: Double, feedback: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var scoreText by remember(entrega.id) {
        mutableStateOf(entrega.score?.toString() ?: "")
    }
    var feedbackText by remember(entrega.id) {
        mutableStateOf(entrega.feedback.orEmpty())
    }
    var parseError by remember { mutableStateOf<String?>(null) }
    val invalidScoreMessage = stringResource(R.string.grade_invalid_score)
    val displayTitle = entrega.studentEnrollment?.student?.let {
        "${it.name} ${it.surname} ${it.ndSurname}".trim()
    } ?: entrega.task?.title ?: stringResource(R.string.error_unknown)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = displayTitle,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        entrega.submissionDate?.let { fecha ->
            Text(
                text = stringResource(R.string.grade_submission_date, fecha),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        OutlinedTextField(
            value = scoreText,
            onValueChange = {
                scoreText = it
                parseError = null
            },
            label = { Text(stringResource(R.string.grade_label_score)) },
            singleLine = true,
            enabled = !guardando,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth(),
            isError = parseError != null,
            supportingText = parseError?.let { { Text(it) } }
        )

        OutlinedTextField(
            value = feedbackText,
            onValueChange = { feedbackText = it },
            label = { Text(stringResource(R.string.grade_label_feedback)) },
            maxLines = 4,
            enabled = !guardando,
            modifier = Modifier.fillMaxWidth()
        )

        if (guardando) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                val score = scoreText.replace(',', '.').toDoubleOrNull()
                if (score == null) {
                    parseError = invalidScoreMessage
                    return@Button
                }
                onGuardar(score, feedbackText)
            },
            enabled = !guardando,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = if (guardando) {
                    stringResource(R.string.grade_saving)
                } else {
                    stringResource(R.string.grade_button_save)
                }
            )
        }
    }
}

@Composable
private fun ErrorBlock(
    mensaje: String,
    onReintentar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.grade_error_loading),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = mensaje,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
        Button(onClick = onReintentar) {
            Text(stringResource(R.string.grade_retry))
        }
    }
}
