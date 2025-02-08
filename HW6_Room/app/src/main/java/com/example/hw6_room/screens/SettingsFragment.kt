package com.example.hw6_room.screens


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.hw6_room.R
import com.example.hw6_room.databinding.FragmentSettingsBinding
import com.example.hw6_room.utils.Constants
import com.example.hw6_room.utils.SessionManager
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.github.dhaval2404.colorpicker.model.ColorSwatch

class SettingsFragment : Fragment() {

    private var viewBinding: FragmentSettingsBinding? = null
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentSettingsBinding.inflate(inflater, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(requireContext())
        initUI()
    }

    private fun initUI() {
        initThemeChanger()
        initExitButton()
    }

    private fun initThemeChanger() {
        viewBinding?.buttonColor?.setOnClickListener {
            showColorPicker()
        }
    }

    private fun showColorPicker() {
        MaterialColorPickerDialog
            .Builder(requireContext())
            .setTitle(R.string.choose_color)
            .setColors(getColorList())
            .setColorShape(ColorShape.CIRCLE)
            .setColorSwatch(ColorSwatch._300)
            .setDefaultColor(R.color.dark_blue_main)
            .setColorListener { color, _ ->
                updateButtonColor(color)
                setAppTheme(color)
            }
            .show()
    }

    private fun getColorList(): List<String> {
        return resources.getStringArray(R.array.color_array).toList()
    }


    private fun updateButtonColor(color: Int) {
        viewBinding?.buttonColor?.setBackgroundColor(color)
    }

    private fun initExitButton() {
        viewBinding?.exitButton?.setOnClickListener {
            logoutUser()
        }
    }

    private fun setAppTheme(theme: Int) {
        requireContext().getSharedPreferences(Constants.APP_REFERENCES, Context.MODE_PRIVATE)
            .edit()
            .putInt(Constants.APP_THEME, theme)
            .apply()
        restartActivity()
    }

    private fun restartActivity() {
        requireActivity().apply {
            finish()
            startActivity(intent)
        }
    }

    private fun logoutUser() {
        sessionManager.logoutUser()
        findNavController().navigate(R.id.action_settingsFragment_to_welcomeFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}
