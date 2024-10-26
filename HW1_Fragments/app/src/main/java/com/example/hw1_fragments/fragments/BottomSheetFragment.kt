package com.example.hw1_fragments.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hw1_fragments.R
import com.example.hw1_fragments.databinding.DialogBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment : BottomSheetDialogFragment(R.layout.dialog_bottom_sheet) {

    private var viewBinding: DialogBottomSheetBinding? = null
    private val mainContainerId = R.id.main_fragment_container

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = DialogBottomSheetBinding.inflate(inflater, container, false)
        viewBinding?.run {
            bottomSheetButton.isEnabled = false
            bottomSheetEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // Проверяем, есть ли текст в EditText
                    bottomSheetButton.isEnabled = s?.isNotEmpty() == true
                }
                override fun afterTextChanged(s: Editable?) {}
            })

            bottomSheetButton.setOnClickListener {
                val firstFragment = FirstFragment.newInstance(bottomSheetEditText.text.toString())
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(mainContainerId, firstFragment)
                    .commit()
            }
        }
        return viewBinding?.root
    }

    override fun onDestroyView() {
        viewBinding = null
        super.onDestroyView()
    }
    companion object
    {
        const val TAG = "BOTTOM_SHEET"
    }
}
