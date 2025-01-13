package com.example.hw4_themes_notifications

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.hw4_themes_notifications.fragments.MainFragment
import com.example.hw4_themes_notifications.utils.Constants

class MainActivity : AppCompatActivity() {

    private var currentThemeId: Int = R.style.Theme_HW4_Themes_Notifications // Стартовая тема

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            currentThemeId = savedInstanceState.getInt(Constants.THEME_KEY, R.style.Theme_HW4_Themes_Notifications)
        }
        setTheme(currentThemeId)
        setContentView(R.layout.activity_main)

        // Проверяем разрешения
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            checkAndRequestPermission(android.Manifest.permission.POST_NOTIFICATIONS, ::requestNotificationPermission)
        }
        checkAndRequestPermission(getMediaPermission(), ::requestMediaPermission)

        // Загружаем MainFragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, MainFragment())
                .commit()
        }
    }

    // Универсальный метод для проверки и запроса разрешений
    private fun checkAndRequestPermission(permission: String, requestPermissionFunc: () -> Unit) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionFunc()
        }
    }

    // Возвращает нужное разрешение для доступа к медиа
    private fun getMediaPermission(): String {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        } else {
            android.Manifest.permission.READ_MEDIA_IMAGES
        }
    }

    // Метод для запроса разрешений на уведомления
    private fun requestNotificationPermission() {
        val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            showToast(if (isGranted) Constants.NOTIFICATIONS_ALLOWED else Constants.NOTIFICATIONS_PROHIBITED)
        }
        requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
    }

    // Метод для запроса разрешений на доступ к медиа
    private fun requestMediaPermission() {
        val permission = getMediaPermission()

        val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            showToast(if (isGranted) Constants.MEDIA_ALLOWED else Constants.MEDIA_PROHIBITED)
        }
        requestPermissionLauncher.launch(permission)
    }

    // Вспомогательная функция для отображения Toast сообщений
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    // Метод для изменения темы из Fragment
    fun changeTheme(themeId: Int) {
        currentThemeId = themeId
        setTheme(currentThemeId)
        recreate()
    }

    // Сохранение текущей темы перед уничтожением активности
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(Constants.THEME_KEY, currentThemeId) // Сохраняем текущую тему
    }
}
