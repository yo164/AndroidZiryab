package com.alanturin.primerbocetoui.ui.profesor.justificaraistencias.confirmajustificacion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alanturin.primerbocetoui.data.repository.assistance.AssistanceRepository
import com.alanturin.primerbocetoui.data.repository.notifications.NotificationsRepository
import com.alanturin.primerbocetoui.ui.session.JustificacionSessionService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class JustificarViewModel @Inject constructor(
    private val justificacionSessionService: JustificacionSessionService,
    private val assistanceRepository: AssistanceRepository,
    private val notificationsRepository: NotificationsRepository
) : ViewModel() {

    val uri = justificacionSessionService.uri
    val idAssistance = justificacionSessionService.idAssistance
    val today: LocalDate = LocalDate.now()


    private val _justified = MutableStateFlow(false)
    val justified: StateFlow<Boolean> = _justified.asStateFlow()

    fun justificar() {
        val id = idAssistance.value ?: return
        viewModelScope.launch {
            assistanceRepository.justifyAssistancebyId(id)
            notificationsRepository.updateStatus(id, "VIEWED", today.toString() )
            _justified.value = true

        }
    }
}