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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentFirstBinding.inflate(inflater, container, false)
        viewBinding?.run {
            enterToSecondScreenButton.setOnClickListener {

                val userText = firstScreenEditText.text.toString()
                val secondFragment = SecondFragment.newInstance(userText)
                parentFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_container, secondFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
        return viewBinding?.root
    }

    override fun onDestroyView() {
        viewBinding = null;
        super.onDestroyView()
    }
}