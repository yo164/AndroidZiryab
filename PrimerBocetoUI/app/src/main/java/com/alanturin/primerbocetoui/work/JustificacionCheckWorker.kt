package com.alanturin.primerbocetoui.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.alanturin.primerbocetoui.data.repository.assistance.AssistanceRepository
import com.alanturin.primerbocetoui.notifications.NotificationHelper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class JustificationCheckWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val assistanceRepository: AssistanceRepository,
    private val notificationHelper: NotificationHelper
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val idTeacher = inputData.getInt("idTeacher", 0)
            if (idTeacher == 0) return Result.failure()

            val pending = assistanceRepository.getPendingJustifications(idTeacher)

            pending.onSuccess { lista ->
                lista.forEach { asistencia ->
                    android.util.Log.d("ZIRYAB", "Justificación pendiente id: ${asistencia.id}")
                    notificationHelper.showJustificationNotification(asistencia.id)
                }
            }
            Result.success()
        } catch (e: Exception) {
            android.util.Log.e("ZIRYAB", "Error en worker: ${e.message}")
            Result.failure()
        }
    }
}