package com.example.hw4_themes_notifications.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.hw4_themes_notifications.MainActivity
import com.example.hw4_themes_notifications.R

class NotificationHandler(private val context: Context) {

    private val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private val channelId = Constants.CHANNEL_ID

    // Метод для создания или обновления канала уведомлений
    private fun createOrUpdateNotificationChannel(importance: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Удаляем старый канал, если он существует
            notificationManager.deleteNotificationChannel(channelId)

            // Создаем новый канал с обновленной важностью
            val channel = NotificationChannel(
                channelId,
                Constants.CHANNEL_NAME,
                when (importance) {
                    0 -> NotificationManager.IMPORTANCE_MAX
                    1 -> NotificationManager.IMPORTANCE_HIGH
                    2 -> NotificationManager.IMPORTANCE_DEFAULT
                    3 -> NotificationManager.IMPORTANCE_LOW
                    else -> NotificationManager.IMPORTANCE_DEFAULT
                }
            )

            notificationManager.createNotificationChannel(channel)
        }
    }

    // Метод для отображения уведомления
    fun showNotification(title: String, text: String, importance: Int) {
        // Создаем или обновляем канал уведомлений с новой важностью
        createOrUpdateNotificationChannel(importance)

        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(text)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(Constants.NOTIFICATION_ID, notification)
    }
}
