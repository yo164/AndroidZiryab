package com.alanturin.primerbocetoui.ui.gestion

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GestionAcademicaViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _uiState: MutableStateFlow<GestionUiState> =
        MutableStateFlow(value = GestionUiState.Initial)

    val uiState: StateFlow<GestionUiState>
        get() = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.value = GestionUiState.Loading

            kotlinx.coroutines.delay(500)

            val menuOptions = getMenuOptions()
            _uiState.value = GestionUiState.Success(menuOptions)
        }
    }

    private fun getMenuOptions(): List<GestionItemUiState> {
        return listOf(
            GestionItemUiState(
                id = 1,
                titleResId = com.alanturin.primerbocetoui.R.string.menu_ficha_usuario,
                icon = Icons.Default.Person,
                backgroundColor = Color(0xFFF3E8FF),
                borderColor = Color(0xFFE9D5FF),
                iconColor = Color(0xFF7C3AED)
            ),
            GestionItemUiState(
                id = 2,
                titleResId = com.alanturin.primerbocetoui.R.string.menu_horario,
                icon = Icons.Default.Schedule,
                backgroundColor = Color(0xFFCCFBF1),
                borderColor = Color(0xFF99F6E0),
                iconColor = Color(0xFF14B8A6)
            ),
            GestionItemUiState(
                id = 3,
                titleResId = com.alanturin.primerbocetoui.R.string.menu_calendario,
                icon = Icons.Default.CalendarToday,
                backgroundColor = Color(0xFFFEF3C7),
                borderColor = Color(0xFFFCD34D),
                iconColor = Color(0xFFF59E0B)
            ),
            GestionItemUiState(
                id = 4,
                titleResId = com.alanturin.primerbocetoui.R.string.menu_tablon,
                icon = Icons.Default.Notifications,
                backgroundColor = Color(0xFFFCE7F3),
                borderColor = Color(0xFF936E9D),
                iconColor = Color(0xFFFF6881)
            )
        )
    }
}

sealed class GestionUiState {
    object Initial: GestionUiState()
    object Loading: GestionUiState()
    data class Success(
        val options: List<GestionItemUiState>
    ): GestionUiState()
}

data class GestionItemUiState(
    val id: Long,
    val titleResId: Int,
    val icon: ImageVector,
    val backgroundColor: Color,
    val borderColor: Color,
    val iconColor: Color
)
