package com.example.hw4_themes_notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.hw4_themes_notifications.databinding.FragmentMainBinding

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
        pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                viewBinding?.imageView?.setImageURI(uri) // Устанавливаем изображение
                viewBinding?.deleteButton?.visibility = View.VISIBLE
            } else {
                Toast.makeText(requireContext(), Constants.IMAGE_NOT_SELECTED, Toast.LENGTH_SHORT).show()
            }
        }

        // Нажатие на ImageView, чтобы открыть галерею
        viewBinding?.imageView?.setOnClickListener {
            pickImageLauncher.launch(Constants.IMAGE_INPUT) // Открываем галерею
        }

        // Инициализация NotificationHandler
        notificationHandler = NotificationHandler(requireContext())

        // Слушатели кнопок и действий
        setupListeners()
        setupSpinner()
    }

    private fun setupListeners() {
        viewBinding?.showColorsButton?.setOnClickListener {
            toggleColorSelection()
        }

        viewBinding?.resetColorButton?.setOnClickListener {
            changeTheme(R.style.Theme_HW4_Themes_Notifications)
        }

        viewBinding?.redColorView?.setOnClickListener {
            changeTheme(R.style.Theme_Red)
        }

        viewBinding?.yellowColorView?.setOnClickListener {
            changeTheme(R.style.Theme_Yellow)
        }

        viewBinding?.greenColorView?.setOnClickListener {
            changeTheme(R.style.Theme_Green)
        }


        viewBinding?.deleteButton?.setOnClickListener {
            viewBinding?.imageView?.setImageResource(R.drawable.rounded_background)
            viewBinding?.deleteButton?.visibility = View.GONE
        }

        viewBinding?.showNotificationButton?.setOnClickListener {
            val title = viewBinding?.notificationTitle?.text.toString()
            val text = viewBinding?.notificationText?.text.toString()

            if (title.isEmpty() || text.isEmpty()) {
                Toast.makeText(requireContext(), Constants.FILL_FIELDS_REQUIREMENT, Toast.LENGTH_SHORT).show()
            } else {
                notificationHandler.showNotification(title, text, currentImportance)
            }
        }
    }

    private fun setupSpinner() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.priority_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            viewBinding?.prioritySpinner?.adapter = adapter
        }

        viewBinding?.prioritySpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                currentImportance = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun toggleColorSelection() {
        if (viewBinding?.colorSelectionContainer?.visibility == View.GONE) {
            viewBinding?.colorSelectionContainer?.visibility = View.VISIBLE
            viewBinding?.guideline?.setGuidelinePercent(0.35f)
        } else {
            viewBinding?.colorSelectionContainer?.visibility = View.GONE
            viewBinding?.guideline?.setGuidelinePercent(0.25f)
        }
    }

    private fun changeTheme(themeId: Int) {
        (requireActivity() as MainActivity).changeTheme(themeId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}
