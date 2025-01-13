package com.example.hw4_themes_notifications

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ThemeUtils

class MainActivity : AppCompatActivity() {

    private var currentThemeId: Int = R.style.Theme_HW4_Themes_Notifications // Стартовая тема

    override fun onCreate(savedInstanceState: Bundle?) {

        if (savedInstanceState != null) {
            currentThemeId = savedInstanceState.getInt(Constants.THEME_KEY, R.style.Theme_HW4_Themes_Notifications)
        }
        setTheme(currentThemeId)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Проверяем разрешения
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            checkNotificationPermission()
        }

        // Загружаем MainFragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, MainFragment())
                .commit()
        }
    }

    private fun checkNotificationPermission() {
        if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            requestNotificationPermission()
        }
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


    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val requestPermissionLauncher =
                registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                    if (isGranted) {
                        Toast.makeText(this, Constants.NOTIFICATIONS_ALLOWED, Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(this, Constants.NOTIFICATIONS_PROHIBITED, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}
