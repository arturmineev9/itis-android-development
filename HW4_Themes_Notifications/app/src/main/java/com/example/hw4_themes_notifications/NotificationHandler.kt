package com.example.hw4_themes_notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

class NotificationHandler(private val context: Context) {

    private val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private val channelId = Constants.CHANNEL_ID

    private fun createNotificationChannel(importance: Int)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
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

    fun showNotification(title: String, text: String, importance: Int) {
        createNotificationChannel(importance)

        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
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
