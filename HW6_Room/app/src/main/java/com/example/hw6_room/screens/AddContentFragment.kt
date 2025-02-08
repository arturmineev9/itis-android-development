package com.example.hw6_room.screens

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.hw6_room.MainActivity
import com.example.hw6_room.R
import com.example.hw6_room.databinding.FragmentAddContentBinding
import com.example.hw6_room.di.ServiceLocator
import com.example.hw6_room.utils.Constants
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
        (activity as MainActivity).setBottomNavigationVisibility(Constants.BOTTOM_NAVIGATION_VISIBILITY_FALSE)
        sessionManager = SessionManager(requireContext())
        initializeGalleryLauncher()
        downloadingImageByUrl()
        viewBinding?.run {
            radioGroupSource.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.rbUrl -> {
                        hideKeyboard()
                        etUrl.visibility = View.VISIBLE
                        btnSelectImage.visibility = View.GONE
                        btnDownloadImage.visibility = View.VISIBLE
                    }

                    R.id.rbGallery -> {
                        hideKeyboard()
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
                viewBinding?.ivPreview?.setImageURI(uri)
                selectedImageUri = uri
            } else {
                Toast.makeText(requireContext(), getString(R.string.image_not_selected_message), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun downloadingImageByUrl() {
        viewBinding?.run {
            btnDownloadImage.setOnClickListener {
                val url = etUrl.text.toString().trim()
                if (url.isNotEmpty()) {
                    Glide.with(requireContext())
                        .load(url)
                        .into(ivPreview)
                } else {
                    ivPreview.setImageResource(0)
                }
            }
        }
    }

    // Запуск выбора изображения
    private fun openGallery() {
        pickImageLauncher.launch(Constants.IMAGE_TYPE) // Открываем только изображения
    }

    private fun saveMeme() {
        viewBinding?.run {
            val title = etTitle.text.toString().trim()
            val description = etDescription.text.toString().trim()
            val source = if (radioGroupSource.checkedRadioButtonId == rbUrl.id) Constants.SOURCE_INTERNET else Constants.SOURCE_GALLERY
            val url = if (source == Constants.SOURCE_INTERNET) etUrl.text.toString().trim() else selectedImageUri.toString()

            if (title.isEmpty() || url.isEmpty()) {
                Toast.makeText(requireContext(), getString(R.string.fill_all_fields_message), Toast.LENGTH_SHORT).show()
                return
            }

            val userId = sessionManager.getUserId()
            lifecycleScope.launch {
                memeRepository.addMeme(title, description, url, source, userId)
                Toast.makeText(requireContext(), getString(R.string.meme_added_message), Toast.LENGTH_SHORT).show()
                activity?.let {
                    val navController = Navigation.findNavController(it, R.id.container)
                    navController.popBackStack()
                }
            }
        }
    }

    private fun hideKeyboard() {
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocusView = view?.rootView?.findFocus()
        currentFocusView?.let {
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as MainActivity).setBottomNavigationVisibility(Constants.BOTTOM_NAVIGATION_VISIBILITY_TRUE)
        viewBinding = null
    }
}
