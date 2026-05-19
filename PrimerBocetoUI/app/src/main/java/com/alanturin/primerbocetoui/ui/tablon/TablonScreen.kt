package com.alanturin.primerbocetoui.ui.tablon

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.alanturin.primerbocetoui.R
import com.alanturin.primerbocetoui.domain.model.Announcement

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TablonScreen(
    modifier: Modifier = Modifier,
    viewModel: TablonViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val creationState by viewModel.creationState.collectAsState()
    val canPublish = viewModel.canPublish()
    val context = LocalContext.current

    var showDialog by remember { mutableStateOf(false) }

    // Manejar el estado de creación del anuncio
    LaunchedEffect(creationState) {
        when (creationState) {
            is TablonViewModel.CreationState.Success -> {
                Toast.makeText(context, context.getString(R.string.tablon_success_published), Toast.LENGTH_SHORT).show()
                showDialog = false
                viewModel.resetCreationState()
            }
            is TablonViewModel.CreationState.Error -> {
                val errorMsg = (creationState as TablonViewModel.CreationState.Error).message
                Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show()
                viewModel.resetCreationState()
            }
            else -> {}
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        floatingActionButton = {
            if (canPublish) {
                FloatingActionButton(
                    onClick = { showDialog = true },
                    containerColor = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else Color(0xFF7C3AED),
                    contentColor = Color.White,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.padding(bottom = 16.dp, end = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(id = R.string.tablon_btn_publish)
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Spacer(modifier = Modifier.height(20.dp))

                // Encabezado
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.menu_tablon),
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else Color(0xFF7C3AED)
                        )
                    )

                    IconButton(
                        onClick = { viewModel.cargarAnuncios() },
                        enabled = uiState !is TablonViewModel.UiState.Loading
                    ) {
                        if (uiState is TablonViewModel.UiState.Loading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                strokeWidth = 2.dp,
                                color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else Color(0xFF7C3AED)
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = stringResource(id = R.string.alumno_clases_cd_reload),
                                tint = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else Color(0xFF7C3AED)
                            )
                        }
                    }
                }

                // Estados de la interfaz
                when (uiState) {
                    is TablonViewModel.UiState.Loading -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else Color(0xFF7C3AED))
                        }
                    }
                    is TablonViewModel.UiState.Empty -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(
                                text = stringResource(id = R.string.tablon_empty),
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Gray
                            )
                        }
                    }
                    is TablonViewModel.UiState.Success -> {
                        val announcements = (uiState as TablonViewModel.UiState.Success).announcements
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            contentPadding = PaddingValues(bottom = 80.dp, top = 8.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(announcements) { announcement ->
                                AnnouncementCard(announcement = announcement)
                            }
                        }
                    }
                    is TablonViewModel.UiState.Error -> {
                        val errorMsg = (uiState as TablonViewModel.UiState.Error).message
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Card(
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF2F2)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = errorMsg,
                                    color = Color.Red,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Diálogo de creación
            if (showDialog) {
                CreateAnnouncementDialog(
                    onDismiss = { showDialog = false },
                    onPublish = { title, body -> viewModel.crearAnuncio(title, body) },
                    isLoading = creationState is TablonViewModel.CreationState.Loading
                )
            }
        }
    }
}

@Composable
fun AnnouncementCard(announcement: Announcement) {
    val isDark = isSystemInDarkTheme()
    val cleanDate = announcement.createdAt.substringBefore("T")

    // Colores premium con gradientes suaves de púrpura/lavanda
    val cardBackgroundColors = if (isDark) {
        listOf(Color(0xFF2D1B4E), Color(0xFF1F1135))
    } else {
        listOf(Color(0xFFF3E8FF), Color(0xFFFAF5FF))
    }

    val accentColor = if (isDark) MaterialTheme.colorScheme.primary else Color(0xFF7C3AED)
    val textColor = if (isDark) Color.White else Color(0xFF111827)
    val bodyColor = if (isDark) Color(0xFFD1D5DB) else Color(0xFF374151)
    val subtextColor = if (isDark) Color(0xFF9CA3AF) else Color(0xFF6B7280)

    Card(
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = if (isDark) Color(0xFF1E1B29) else Color.White)
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(colors = cardBackgroundColors)
                )
                .padding(20.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Título
                Text(
                    text = announcement.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        letterSpacing = (-0.3).sp
                    ),
                    color = textColor,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Separador sutil
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(accentColor.copy(alpha = 0.2f))
                        .padding(vertical = 4.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Cuerpo
                Text(
                    text = announcement.body,
                    style = MaterialTheme.typography.bodyMedium,
                    color = bodyColor,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Autor y Fecha
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val authorName = announcement.creatorName ?: stringResource(id = R.string.menu_default_name)
                    Text(
                        text = stringResource(id = R.string.tablon_author, authorName),
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = accentColor
                    )

                    Text(
                        text = stringResource(id = R.string.tablon_published_at, cleanDate),
                        style = MaterialTheme.typography.labelSmall,
                        color = subtextColor
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAnnouncementDialog(
    onDismiss: () -> Unit,
    onPublish: (String, String) -> Unit,
    isLoading: Boolean
) {
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }

    val isDark = isSystemInDarkTheme()
    val dialogBg = if (isDark) Color(0xFF1E1B29) else Color.White

    Dialog(onDismissRequest = { if (!isLoading) onDismiss() }) {
        Card(
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = dialogBg)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.tablon_dialog_title),
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = if (isDark) Color.White else Color(0xFF1F2937),
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                // Campo de Título
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text(text = stringResource(id = R.string.tablon_label_title)) },
                    singleLine = true,
                    enabled = !isLoading,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF7C3AED),
                        focusedLabelColor = Color(0xFF7C3AED)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo de Cuerpo (Multi-line)
                OutlinedTextField(
                    value = body,
                    onValueChange = { body = it },
                    label = { Text(text = stringResource(id = R.string.tablon_label_body)) },
                    minLines = 4,
                    maxLines = 8,
                    enabled = !isLoading,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF7C3AED),
                        focusedLabelColor = Color(0xFF7C3AED)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Botones
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = onDismiss,
                        enabled = !isLoading,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.task_button_cancel),
                            color = Color.Gray
                        )
                    }

                    Button(
                        onClick = { onPublish(title, body) },
                        enabled = !isLoading && title.isNotBlank() && body.isNotBlank(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF7C3AED)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.height(44.dp)
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                strokeWidth = 2.dp,
                                color = Color.White
                            )
                        } else {
                            Text(text = stringResource(id = R.string.task_button_create))
                        }
                    }
                }
            }
        }
    }
}
