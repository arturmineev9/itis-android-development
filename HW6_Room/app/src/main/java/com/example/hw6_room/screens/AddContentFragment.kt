package com.example.hw6_room.screens

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.hw6_room.MainActivity
import com.example.hw6_room.R
import com.example.hw6_room.databinding.FragmentAddContentBinding
import com.example.hw6_room.di.ServiceLocator
import com.example.hw6_room.utils.SessionManager
import kotlinx.coroutines.launch


class AddContentFragment : Fragment() {

    private var viewBinding: FragmentAddContentBinding? = null
    private val memeRepository = ServiceLocator.getMemeRepository()
    private var selectedImageUri: Uri? = null
    private lateinit var sessionManager: SessionManager
    private lateinit var pickImageLauncher: ActivityResultLauncher<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentAddContentBinding.inflate(inflater, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setBottomNavigationVisibility(false)
        sessionManager = SessionManager(requireContext())
        initializeGalleryLauncher()
        downloadingImageByUrl()
        viewBinding?.run {
            radioGroupSource.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.rbUrl -> {
                        etUrl.visibility = View.VISIBLE
                        btnSelectImage.visibility = View.GONE
                        btnDownloadImage.visibility = View.VISIBLE
                    }
                    R.id.rbGallery -> {
                        btnDownloadImage.visibility = View.GONE
                        etUrl.visibility = View.GONE
                        btnSelectImage.visibility = View.VISIBLE
                        ivPreview.visibility = View.VISIBLE
                    }
                }
            }

            btnSelectImage.setOnClickListener {
                openGallery()
            }

            btnSaveMeme.setOnClickListener {
                saveMeme()
            }
        }
    }

    private fun initializeGalleryLauncher() {
        pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                viewBinding?.ivPreview?.setImageURI(uri) // Отображаем изображение в ImageView
                selectedImageUri = uri // Сохраняем URI для отправки в БД
            } else {
                Toast.makeText(requireContext(), "Изображение не выбрано", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun downloadingImageByUrl() {
        viewBinding?.run {
            btnDownloadImage.setOnClickListener {
                val url = etUrl.text.toString().trim()
                if (url.isNotEmpty()) {
                    Glide.with(requireContext())
                        .load("https://images.squarespace-cdn.com/content/v1/54822a56e4b0b30bd821480c/45ed8ecf-0bb2-4e34-8fcf-624db47c43c8/Golden+Retrievers+dans+pet+care.jpeg")  // Загружаем изображение по URL
                        .into(ivPreview)  // Загружаем в ImageView
                } else {
                    ivPreview.setImageResource(0)  // Очистить изображение, если URL пустой
                }
            }
        }
    }

    // Запуск выбора изображения
    private fun openGallery() {
        pickImageLauncher.launch("image/*") // Открываем только изображения
    }


    private fun saveMeme() {
        viewBinding?.run {
            val title = etTitle.text.toString().trim()
            val description = etDescription.text.toString().trim()
            val tags = etTags.text.toString().trim()
            val source =
                if (radioGroupSource.checkedRadioButtonId == rbUrl.id) "internet" else "gallery"
            val url = if (source == "internet") etUrl.text.toString()
                .trim() else selectedImageUri.toString()

            if (title.isEmpty() || url.isEmpty()) {
                Toast.makeText(requireContext(), "Заполните все поля!", Toast.LENGTH_SHORT).show()
                return
            }

            val userId = sessionManager.getUserId()
            lifecycleScope.launch {
                memeRepository.addMeme(title, description, url, source, tags, userId)
                Toast.makeText(requireContext(), "Мем добавлен!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as MainActivity).setBottomNavigationVisibility(true)
        viewBinding = null
    }
}