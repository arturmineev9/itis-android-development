package ru.itis.clientserverapp

import android.app.NotificationManager
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import ru.itis.clientserverapp.app.R
import androidx.core.content.edit
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings

class AppMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val data = remoteMessage.data
        val category = data[CATEGORY_KEY]

        when (category) {
            CATEGORY_NOTIFICATION -> {
                val title = data[TITLE_KEY] ?: getString(R.string.default_notification_title)
                val message = data[MESSAGE_KEY] ?: getString(R.string.default_notification_message)
                showNotification(title, message)
            }

            CATEGORY_SAVE_DATA -> {
                val value = data[VALUE_KEY] ?: return
                saveToPrefs(CATEGORY_SAVE_DATA, value)
            }

            CATEGORY_OPEN_FEATURE -> {
                val remoteConfig = Firebase.remoteConfig

                val configSettings = remoteConfigSettings {
                    minimumFetchIntervalInSeconds = 0
                }
                remoteConfig.setConfigSettingsAsync(configSettings)
                remoteConfig.fetchAndActivate()

                if (AppLifecycleObserver.isAppInForeground()) {
                    val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
                    val isAccessGranted = remoteConfig.getBoolean(GRAPH_SCREEN_ACCESS_FLAG)
                    prefs.edit {
                        putBoolean(SHOULD_OPEN_GRAPH_FRAGMENT, isAccessGranted)
                    }
                }
            }
        }
    }

    private fun showNotification(title: String, message: String) {
        val builder = NotificationCompat.Builder(this, DEFAULT_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notifications)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_MAX)

        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(DEFAULT_NOTIFICATION_ID, builder.build())
    }

    private fun saveToPrefs(key: String, value: String) {
        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        prefs.edit { putString(key, value) }
    }

    companion object {
        const val CATEGORY_KEY = "category"
        const val CATEGORY_NOTIFICATION = "show_notification"
        const val CATEGORY_SAVE_DATA = "save_data"
        const val CATEGORY_OPEN_FEATURE = "open_feature"

        const val TITLE_KEY = "title"
        const val MESSAGE_KEY = "message"
        const val VALUE_KEY = "value"

        const val PREFS_NAME = "fcm_prefs"
        const val SHOULD_OPEN_GRAPH_FRAGMENT = "shouldOpenGraphFragment"
        const val GRAPH_SCREEN_ACCESS_FLAG = "graph_screen_access_flag"

        const val DEFAULT_CHANNEL_ID = "default_channel_id"
        const val DEFAULT_NOTIFICATION_ID = 1001
    }
}
