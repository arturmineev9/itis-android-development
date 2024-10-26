package com.example.hw1_fragments.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hw1_fragments.R
import com.example.hw1_fragments.databinding.FragmentFirstBinding


class FirstFragment : Fragment(R.layout.fragment_first) {

    private var viewBinding: FragmentFirstBinding? = null;
    private val mainContainerId = R.id.main_fragment_container

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentFirstBinding.inflate(inflater, container, false)
        val bottomSheetText = arguments?.getString("arg_bottom_sheet_text") ?: ""
        viewBinding?.run {
            firstScreenEditText.setText(bottomSheetText)
            enterToSecondScreenButton.setOnClickListener {
                val userText = firstScreenEditText.text.toString()
                val secondFragment = SecondFragment.newInstance(userText)
                parentFragmentManager.beginTransaction()
                    .replace(mainContainerId, secondFragment)
                    .addToBackStack(null)
                    .commit()
            }

            enterToThirdScreenButton.setOnClickListener {
                val userText = firstScreenEditText.text.toString()

                val secondFragment = SecondFragment.newInstance(userText)
                parentFragmentManager.beginTransaction()
                    .replace(mainContainerId, secondFragment)
                    .addToBackStack(null)
                    .commit()

                val thirdFragment = ThirdFragment.newInstance(userText)
                parentFragmentManager.beginTransaction()
                    .replace(mainContainerId, thirdFragment)
                    .addToBackStack(null)
                    .commit()
            }

            openBottomSheetButton?.setOnClickListener {
                val dialog = BottomSheetFragment().apply {
                    isCancelable = true
                }

                dialog.show(childFragmentManager, BottomSheetFragment.TAG)
            }
        }
        return viewBinding?.root
    }

    override fun onDestroyView() {
        viewBinding = null
        super.onDestroyView()
    }

    companion object {
        private const val ARG_ENTERED_TEXT = "arg_bottom_sheet_text"

        fun newInstance(enteredText: String) = FirstFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_ENTERED_TEXT, enteredText)
            }
        }
    }

}
