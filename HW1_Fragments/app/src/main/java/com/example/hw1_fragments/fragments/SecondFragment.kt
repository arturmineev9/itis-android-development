package com.example.hw1_fragments.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hw1_fragments.R
import com.example.hw1_fragments.databinding.FragmentFirstBinding
import com.example.hw1_fragments.databinding.FragmentSecondBinding


class SecondFragment : Fragment(R.layout.fragment_second) {

    private var viewBinding: FragmentSecondBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentSecondBinding.inflate(inflater, container, false)
        val enteredText = arguments?.getString("arg_entered_text") ?: "Текст не передан"
        viewBinding?.run {
            textView.text = enteredText.toString()
        }
        return viewBinding?.root
    }

    companion object {
        private const val ARG_ENTERED_TEXT = "arg_entered_text"

        // Метод для создания нового экземпляра SecondFragment с текстом
        fun newInstance(enteredText: String) = SecondFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_ENTERED_TEXT, enteredText)
            }
        }
    }
}
