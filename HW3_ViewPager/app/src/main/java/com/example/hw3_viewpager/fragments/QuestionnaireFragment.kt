package com.example.hw3_viewpager.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.os.bundleOf
import com.example.hw3_viewpager.R
import com.example.hw3_viewpager.databinding.FragmentQuestionnaireBinding
import com.example.hw3_viewpager.fragments.ViewPagerFragment.Companion.selectedAnswers
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
            getRadioButtons(questionId)
        }

        viewBinding?.radioGroup?.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = group.findViewById<RadioButton>(checkedId)
            val position = group.indexOfChild(radioButton)
            selectedAnswers[questionId] = position
        }

        super.onViewCreated(view, savedInstanceState)
    }


    private fun getRadioButtons(questionId: Int) {
        val radioGroup = viewBinding?.radioGroup
        val answers = QuestionsRepository.getQuestionById(questionId)?.answers
        answers?.forEachIndexed { index, answer ->
            val radioButton = RadioButton(requireContext()).apply {
                id = View.generateViewId()
                text = answer
                layoutParams = RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.MATCH_PARENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT
                )
                setBackgroundResource(R.drawable.radio_button_selector)
                
                // Восстановление состояния: если этот ответ выбран, отмечаем кнопку
                selectedAnswers[questionId]?.let { selectedAnswerPosition ->
                    if (selectedAnswerPosition == index) {
                        isChecked = true
                    }
                }
            }
            radioGroup?.addView(radioButton)
        }
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