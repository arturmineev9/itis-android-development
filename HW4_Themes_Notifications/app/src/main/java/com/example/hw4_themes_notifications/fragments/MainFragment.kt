package com.example.hw4_themes_notifications.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.hw4_themes_notifications.MainActivity
import com.example.hw4_themes_notifications.R
import com.example.hw4_themes_notifications.databinding.FragmentMainBinding
import com.example.hw4_themes_notifications.utils.Constants
import com.example.hw4_themes_notifications.utils.NotificationHandler

class MainFragment : Fragment() {

    private var viewBinding: FragmentMainBinding? = null
    private var currentImportance = 0
    private lateinit var notificationHandler: NotificationHandler
    private lateinit var pickImageLauncher: ActivityResultLauncher<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewBinding = FragmentMainBinding.inflate(inflater, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Инициализация лаунчера для выбора изображения
        initializeImageLauncher()

        notificationHandler = NotificationHandler(requireContext())

        // Слушатели кнопок и спиннера для выбора приоритетов
        setupListeners()
        setupSpinner()
    }

    private fun setupListeners() {
        viewBinding?.run {
            // Кнопка для отображения или скрытия контейнера с цветами
            showColorsButton.setOnClickListener { toggleColorSelection() }

            // Кнопки для смены темы на разные цвета
            redColorView.setOnClickListener { changeTheme(R.style.Theme_Red) }
            yellowColorView.setOnClickListener { changeTheme(R.style.Theme_Yellow) }
            greenColorView.setOnClickListener { changeTheme(R.style.Theme_Green) }

            // Кнопка для сброса темы на начальную
            resetColorButton.setOnClickListener { changeTheme(R.style.Theme_HW4_Themes_Notifications) }

            // Выбор изображения
            imageView.setOnClickListener {
                pickImageLauncher.launch(Constants.IMAGE_INPUT) // Открываем галерею
            }

            // Кнопка для удаления выбранного изображения
            deleteButton.setOnClickListener {
                imageView.setImageResource(R.drawable.rounded_background)
                deleteButton.visibility = View.GONE
            }

            // Кнопка для отображения уведомления
            showNotificationButton.setOnClickListener {
                val title = notificationTitle.text.toString()
                val text = notificationText.text.toString()

                // Проверка, чтобы не показывать уведомление, если поля пустые
                if (title.isEmpty() || text.isEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        Constants.FILL_FIELDS_REQUIREMENT,
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    // Показ уведомления с введенными значениями
                    notificationHandler.showNotification(title, text, currentImportance)
                }
            }
        }
    }

    // Метод для настройки спиннера для выбора важности уведомлений
    private fun setupSpinner() {
        // Создание адаптер для спиннера из ресурса
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.priority_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            viewBinding?.prioritySpinner?.adapter = adapter
        }

        viewBinding?.prioritySpinner?.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    currentImportance = position
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    return
                }
            }
    }

    // Метод для создания отступа при открытии контейнера с цветами
    private fun toggleColorSelection() {
        viewBinding?.run {
            if (colorSelectionContainer.visibility == View.GONE) {
                colorSelectionContainer.visibility = View.VISIBLE
                guideline.setGuidelinePercent(0.35f)
            } else {
                colorSelectionContainer.visibility = View.GONE
                guideline.setGuidelinePercent(0.25f)
            }
        }

    }

    private fun changeTheme(themeId: Int) {
        // Вызов метода активности для смены темы
        (requireActivity() as MainActivity).changeTheme(themeId)
    }

    private fun initializeImageLauncher() {
        // Инициализация лаунчера для выбора изображения
        pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                viewBinding?.imageView?.setImageURI(uri) // Устанавливаем изображение
                viewBinding?.deleteButton?.visibility = View.VISIBLE
            } else {
                Toast.makeText(requireContext(), Constants.IMAGE_NOT_SELECTED, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}
