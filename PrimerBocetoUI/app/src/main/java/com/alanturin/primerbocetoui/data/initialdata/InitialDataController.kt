package com.alanturin.primerbocetoui.data.initialdata

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.alanturin.primerbocetoui.data.repository.WeekScheduleRepository
import com.alanturin.primerbocetoui.data.repository.assistance.AssistanceRepository
import com.alanturin.primerbocetoui.data.repository.studentweekschedule.StudentWeekScheduleRepository
import com.alanturin.primerbocetoui.data.repository.teacher.TeacherRepository
import com.alanturin.primerbocetoui.domain.repository.ClasesAlumnoRepository
import com.alanturin.primerbocetoui.ui.session.SessionViewModel
import com.alanturin.primerbocetoui.work.JustificationCheckWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InitialDataController @Inject constructor(
    private val weekScheduleRepository: WeekScheduleRepository,
    private val studentWeekScheduleRepository: StudentWeekScheduleRepository,
    private val assistanceRepository: AssistanceRepository,
    private val sessionViewModel: SessionViewModel,
    private val teacherRepository: TeacherRepository,
    private val clasesAlumnoRepository: ClasesAlumnoRepository,
    @ApplicationContext private val context: Context

) {
    suspend fun cargarDatosIniciales() {
        val userId = sessionViewModel.userId.value ?: return
        val userRole = sessionViewModel.userRole.value ?: return




        coroutineScope {

            if (userRole == "TEACHER"){
                launch { weekScheduleRepository.getWeekScheduleByTeacher(userId.toLong(), userRole) }
                launch { assistanceRepository.getAll(userId) }
                launch { teacherRepository.getTeacherById(userId) }//falta implementar delete all

            }else{
                launch { studentWeekScheduleRepository.getWeekScheduleByStudent(userId.toLong(), userRole) }
                launch { assistanceRepository.getByStudentId(userId) }
                launch { clasesAlumnoRepository.getClases(userId.toLong()) }


            }
        }
    }



    fun programarWorker(idTeacher: Int) {
        WorkManager.getInstance(context).cancelAllWork()

        val data = workDataOf("idTeacher" to idTeacher)
        val workRequest = PeriodicWorkRequestBuilder<JustificationCheckWorker>(15, TimeUnit.MINUTES)
            .setInputData(data)
            .build()
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "justification_check",
            ExistingPeriodicWorkPolicy.REPLACE,
            workRequest
        )
    }

    suspend fun limpiarDatos() {
        coroutineScope {
            launch { weekScheduleRepository.deleteAll() }
            launch { studentWeekScheduleRepository.deleteAll() }
            //launch { assistanceRepository.deleteAll() }
            launch { clasesAlumnoRepository.deleteAll() }

        }
    }
}