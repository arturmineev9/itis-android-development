package com.example.hw6_room.screens

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hw6_room.R
import com.example.hw6_room.databinding.FragmentMainScreenBinding
import com.example.hw6_room.databinding.FragmentSettingsBinding
import com.example.hw6_room.utils.Constants
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.github.dhaval2404.colorpicker.model.ColorSwatch


class SettingsFragment : Fragment() {

    private var viewBinding: FragmentSettingsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentSettingsBinding.inflate(inflater, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding?.buttonColor?.setOnClickListener {
            MaterialColorPickerDialog
                .Builder(requireContext())
                .setTitle(R.string.choose_color)
                .setColors(
                    arrayListOf(
                        "#E57373",
                        "#f06292",
                        "#ba68c8",
                        "#9575cd",
                        "#7986cb",
                        "#64b5f6",
                        "#4fc3f7",
                        "#4dd0e1",
                        "#4db6ac",
                        "#81c784",
                        "#aed581",
                        "#dce775",
                        "#ffd54f",
                        "#ffb74d",
                        "#ff8a65",
                        "#a1887f",
                        "#90a4ae"
                    )
                )
                .setColorShape(ColorShape.CIRCLE)
                .setColorSwatch(ColorSwatch._300)
                .setDefaultColor(R.color.dark_blue_main)
                .setColorListener { color, colorHex ->
                    viewBinding?.buttonColor?.setBackgroundColor(color)
                    setAppTheme(color)
                }
                .show()
        }
    }

    private fun setAppTheme(theme: Int) {
        val sharedPreferences =
            requireContext().getSharedPreferences(Constants.AppPreferences, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(Constants.AppTheme, theme)
        editor.apply()

        requireActivity().recreate()
    }
}