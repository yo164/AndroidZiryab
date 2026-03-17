package com.alanturin.primerbocetoui.ui.group



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alanturin.primerbocetoui.R
import com.alanturin.primerbocetoui.data.remote.model.Group

@Composable
fun GroupScreen(
    viewModel: GroupViewModel = hiltViewModel()
) {
    LaunchedEffect(true) {
        viewModel.cargaGrupos()
    }

    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFF8FAFC))
    ) {
        Text(
            text = stringResource(R.string.group_title),
            style = MaterialTheme.typography.headlineLarge,
            color = Color(0xFF7C3AED),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        when (val ui = state) {
            is GroupViewModel.UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFF8B5CF6))
                }
            }
            is GroupViewModel.UiState.Error -> {
                Text(text = ui.message, color = Color.Red)
            }
            is GroupViewModel.UiState.Empty -> {
                Text(text = stringResource(R.string.group_empty))
            }
            is GroupViewModel.UiState.Success -> {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(ui.groups) { group ->
                        GroupCard(group)
                    }
                }
            }
        }
    }
}

@Composable
fun GroupCard(group: Group) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = group.name,
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFF1F2937)
            )
            Text(
                text = stringResource(R.string.group_capacity, group.capacity?.toString() ?: stringResource(R.string.group_capacity_undefined)),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}