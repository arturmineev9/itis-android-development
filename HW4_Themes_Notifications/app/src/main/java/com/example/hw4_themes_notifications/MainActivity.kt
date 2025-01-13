package com.example.hw4_themes_notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.hw4_themes_notifications.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // ViewBinding
    private lateinit var binding: ActivityMainBinding
    private lateinit var notificationHandler: NotificationHandler
    private var currentThemeId: Int = R.style.Theme_HW4_Themes_Notifications // Стартовая тема
    private var currentImportance = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        notificationHandler = NotificationHandler(this)
        // Восстанавливаем тему, если состояние было сохранено
        if (savedInstanceState != null) {
            currentThemeId = savedInstanceState.getInt("theme_id", R.style.Theme_HW4_Themes_Notifications)
        }
        setTheme(currentThemeId)
        // Инициализация ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Проверяем разрешения при запуске
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            checkNotificationPermission()
        }


        binding.showColorsButton.setOnClickListener {
            toggleColorSelection()
        }

        // Смена темы с сохранением
        binding.resetColorButton.setOnClickListener {
            setTheme(R.style.Theme_HW4_Themes_Notifications)
        }

        // Изменение темы на красную
        binding.redColorView.setOnClickListener {
            changeTheme(R.style.Theme_Red)
        }
        binding.yellowColorView.setOnClickListener {
            changeTheme(R.style.Theme_Yellow)
        }
        binding.greenColorView.setOnClickListener {
            changeTheme(R.style.Theme_Green)
        }


        // Нажатие на ImageView
        binding.imageView.setOnClickListener {
            Glide.with(this)
                .load("https://i.ytimg.com/vi/4e_76ReV2h8/maxresdefault.jpg")
                .into(binding.imageView)
            binding.deleteButton.visibility = View.VISIBLE
        }

        // Удаление изображения
        binding.deleteButton.setOnClickListener {
            binding.imageView.setImageResource(R.drawable.rounded_background)
            binding.deleteButton.visibility = View.GONE
        }

        // Настройка Spinner для приоритетов
        ArrayAdapter.createFromResource(
            this,
            R.array.priority_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.prioritySpinner.adapter = adapter
        }

        // Слушатель выбора в Spinner
        binding.prioritySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                currentImportance = position
                Log.d("currentImportance", currentImportance.toString())
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                return
            }
        }



        // Кнопка для показа уведомления
        binding.showNotificationButton.setOnClickListener {
            val title = binding.notificationTitle.text.toString()
            val text = binding.notificationText.text.toString()

            if (title.isEmpty() || text.isEmpty()) {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
            } else {
                notificationHandler.showNotification(title, text, currentImportance)
            }
        }

        // Кнопка "Сбросить цвет"
        binding.resetColorButton.setOnClickListener {
            changeTheme(R.style.Theme_HW4_Themes_Notifications)
        }
    }

    // Переключение видимости контейнера с цветами
    private fun toggleColorSelection() {

        if (binding.colorSelectionContainer.visibility == View.GONE) {
            binding.colorSelectionContainer.visibility = View.VISIBLE
            binding.guideline.setGuidelinePercent(0.35f) // перемещаем нижние элементы ниже
        } else {
            binding.colorSelectionContainer.visibility = View.GONE
            binding.guideline.setGuidelinePercent(0.25f) // возвращаем элементы в исходное положение
        }
    }


    private fun changeTheme(themeId: Int) {
        currentThemeId = themeId
        setTheme(currentThemeId)
        recreate()  // Перезапуск активности для применения новой темы
    }

    // Сохранение текущей темы перед уничтожением активности
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("theme_id", currentThemeId) // Сохраняем текущую тему
    }

    private fun checkNotificationPermission() {
        if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // Запрос разрешения
            requestNotificationPermission()
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val requestPermissionLauncher =
                registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                    if (isGranted) {
                        Toast.makeText(this, "Уведомления разрешены", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Уведомления запрещены", Toast.LENGTH_SHORT).show()
                    }
                }

            requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}


