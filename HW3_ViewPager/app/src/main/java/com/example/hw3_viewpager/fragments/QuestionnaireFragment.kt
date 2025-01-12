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

// Фрагмент, представляющий вопрос и ответы к нему
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
        // Получаем ID текущего вопроса из переданных аргументов
        val questionId = arguments?.getInt(POSITION_KEY) ?: -1
        // Получаем вопрос по ID из датасета
        val question = QuestionsRepository.getQuestionById(questionId)
        viewBinding?.run {
            questionTv.text = question?.question // Установка текста вопроса
            getRadioButtons(questionId) // Генерируем варианты ответа
        }

        // Обработчик на RadioGroup для остлеживания выбора пользователя
        viewBinding?.radioGroup?.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = group.findViewById<RadioButton>(checkedId) // Находим прожатую пользователем кнопку
            val position = group.indexOfChild(radioButton) // Определяем позицию выбранной кнопк
            selectedAnswers[questionId] = position // Сохраняем выбранный ответ в хранилище ответов пользователя
        }

        super.onViewCreated(view, savedInstanceState)
    }

    // Метод для генерации RadioButton для вариантов ответа
    private fun getRadioButtons(questionId: Int) {
        val radioGroup = viewBinding?.radioGroup
        val answers = QuestionsRepository.getQuestionById(questionId)?.answers // Получаем список ответов по ID
        answers?.forEachIndexed { index, answer -> // Перебираем варианты ответа с их индексами
            val radioButton = RadioButton(requireContext()).apply {
                id = View.generateViewId() // Генерация уникального ID для каждой кнопки
                text = answer // Установка текста варианта ответа
                layoutParams = RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.MATCH_PARENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT
                )// Установка параметров макета
                setBackgroundResource(R.drawable.radio_button_selector)
                
                // Восстановление состояния: если этот ответ был выбран ранее, отмечаем кнопку
                selectedAnswers[questionId]?.let { selectedAnswerPosition ->
                    if (selectedAnswerPosition == index) {
                        isChecked = true
                    }
                }
            }
            radioGroup?.addView(radioButton) // Добавление кнопки в RadioGroup
        }
    }

    companion object {
        const val POSITION_KEY = "position_key"

        // Создание нового экземпляра фрагмента с передачей позиции вопроса
        fun getInstance(position: Int) = QuestionnaireFragment().apply {
            arguments = bundleOf(POSITION_KEY to position)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}