package com.alanturin.primerbocetoui.notifications


import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.SystemClock
import android.util.Log
import androidx.core.app.NotificationCompat
import com.alanturin.primerbocetoui.R
import com.alanturin.primerbocetoui.data.repository.notifications.NotificationsRepository
import com.alanturin.primerbocetoui.domain.model.NotificationInsertEntity
import com.alanturin.primerbocetoui.domain.model.NotificationItem
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationHelper @Inject constructor(
    @ApplicationContext private val context: Context,
    private val notificationsRepository: NotificationsRepository
) {

    companion object {
        const val CHANNEL_ID = "justification_channel"
        const val CHANNEL_NAME = "Justificaciones pendientes"
    }

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val manager = context.getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }

    fun showJustificationNotification(id: Int) {
        Log.d("ZIRYAB", "Lanzando notificación para id: $id")

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Justificación pendiente")
            .setContentText("Un alumno ha solicitado justificar una falta")
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setAutoCancel(true)
            .build()

        val manager = context.getSystemService(NotificationManager::class.java)
        manager.notify(id, notification)
    }

    suspend fun registerNotification(idAssistencia: Int){
         val notificationItem = NotificationInsertEntity(
            0,
            idAssistance = idAssistencia,
            status = "PENDING",
            createdAt = SystemClock.currentNetworkTimeClock().toString(),
            updatedAt = null
        )
        notificationsRepository.insert(notificationItem)

    }
}