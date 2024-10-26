package com.example.hw1_fragments.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hw1_fragments.R
import com.example.hw1_fragments.databinding.FragmentThirdBinding

class ThirdFragment : Fragment(R.layout.fragment_third) {

    private var viewBinding : FragmentThirdBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentThirdBinding.inflate(inflater, container, false)

        val enteredText = arguments?.getString("arg_entered_text") ?: "null"

        if (enteredText.isEmpty()) {
            viewBinding?.thirdScreenTextView?.text = "Третий экран"
        }
        else {
            viewBinding?.thirdScreenTextView?.text = enteredText
        }

        return viewBinding?.root
    }

    companion object {

        private const val ARG_ENTERED_TEXT = "arg_entered_text"

        fun newInstance(enteredText: String) =
            ThirdFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_ENTERED_TEXT, enteredText)
                }
            }
    }
}