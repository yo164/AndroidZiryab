package com.alanturin.primerbocetoui.data.initialdata

import com.alanturin.primerbocetoui.data.repository.WeekScheduleRepository
import com.alanturin.primerbocetoui.data.repository.assistance.AssistanceRepository
import com.alanturin.primerbocetoui.data.repository.studentweekschedule.StudentWeekScheduleRepository
import com.alanturin.primerbocetoui.data.repository.teacher.TeacherRepository
import com.alanturin.primerbocetoui.ui.session.SessionViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InitialDataController @Inject constructor(
    private val weekScheduleRepository: WeekScheduleRepository,
    private val studentWeekScheduleRepository: StudentWeekScheduleRepository,
    private val assistanceRepository: AssistanceRepository,
    private val sessionViewModel: SessionViewModel,
    private val teacherRepository: TeacherRepository
) {
    suspend fun cargarDatosIniciales() {
        val userId = sessionViewModel.userId.value ?: return
        val userRole = sessionViewModel.userRole.value ?: return




        coroutineScope {

            if (userRole == "TEACHER"){
                launch { weekScheduleRepository.getWeekScheduleByTeacher(userId.toLong(), userRole) }
                launch { assistanceRepository.getAll() }
                launch { teacherRepository.getTeacherById(userId) }

            }else{
                launch { studentWeekScheduleRepository.getWeekScheduleByStudent(userId.toLong(), userRole) }
                launch { assistanceRepository.getByStudentId(userId) }

            }
        }
    }

    suspend fun limpiarDatos() {
        coroutineScope {
            launch { weekScheduleRepository.deleteAll() }
            launch { studentWeekScheduleRepository.deleteAll() }
            launch { assistanceRepository.deleteAll() }
        }
    }
}