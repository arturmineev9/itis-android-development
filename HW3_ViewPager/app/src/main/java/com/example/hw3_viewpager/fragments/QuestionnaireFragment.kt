package com.example.hw3_viewpager.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.example.hw3_viewpager.R
import com.example.hw3_viewpager.databinding.FragmentQuestionnaireBinding
import com.example.hw3_viewpager.repository.QuestionsRepository


class QuestionnaireFragment : Fragment() {


    private var viewBinding: FragmentQuestionnaireBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentQuestionnaireBinding.inflate(inflater, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val questionId = arguments?.getInt(POSITION_KEY) ?: -1
        val question = QuestionsRepository.getQuestionById(questionId)
        viewBinding?.run {

            questionTv.text = question?.question
            radioOne.text = question?.answers?.get(0)
            radioTwo.text = question?.answers?.get(1)
            radioThree.text = question?.answers?.get(2)
            radioFour.text = question?.answers?.get(3)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        const val POSITION_KEY = "position_key"

        fun getInstance(position: Int) = QuestionnaireFragment().apply {
            arguments = bundleOf(POSITION_KEY to position)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}