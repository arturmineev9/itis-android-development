package com.example.hw3_viewpager.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2
import com.example.hw3_viewpager.R
import com.example.hw3_viewpager.adapter.ViewPagerAdapter
import com.example.hw3_viewpager.databinding.FragmentViewPagerBinding
import com.example.hw3_viewpager.repository.QuestionsRepository
import com.google.android.material.snackbar.Snackbar


class ViewPagerFragment : Fragment() {

    private var viewBinding: FragmentViewPagerBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentViewPagerBinding.inflate(inflater, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = ViewPagerAdapter(fragmentManager = parentFragmentManager, lifecycle = this.lifecycle)
        viewBinding?.run {
            viewPager.adapter = adapter
            // Обработчик изменения страницы в ViewPager
            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    val currentPosition = position + 1 // Текущая страница, начинается с 1

                    buttonBack.isEnabled = currentPosition != 1 // Отключение кнопки "Предыдущий", если пользователь на первой странице

                    val isLastQuestion = currentPosition == QuestionsRepository.questions.size // Проверяем, последняя ли это страница
                    buttonNext.isInvisible = isLastQuestion // Скрываем кнопку "Следующий", если это последняя страница
                    buttonComplete.isVisible = isLastQuestion // Показываем кнопку "Завершить", если это последняя страница

                    questionNumberTv.text = "$currentPosition/${QuestionsRepository.questions.size}" // Обновление текста с текущим номером вопроса
                }
            })

            //Инициализация структуры для хранения выбранных ответов
            initSelectedAnswers()

            // Обработка нажатия на кнопку "Предыдущий"
            buttonBack.setOnClickListener {
                viewPager.setCurrentItem(viewPager.currentItem - 1, true)
            }

            // Обработка нажатия на кнопку "Следующий"
            buttonNext.setOnClickListener {
                viewPager.setCurrentItem( viewPager.currentItem + 1, true)
            }

            // Обработка нажатия на кнопку "Завершить"
            buttonComplete.setOnClickListener {
                //Проверяем, ответил ли пользователь на все вопросы
                if (selectedAnswers.values.all { it != null })
                {
                    Snackbar.make(viewPager, R.string.results_are_saved, Snackbar.LENGTH_SHORT).show()
                } else {
                    Snackbar.make(viewPager, R.string.not_all_questions_answered, Snackbar.LENGTH_SHORT).show()
                }

            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        // Хранение ответов пользователя: ключ — ID вопроса, значение — выбранный вариант ответа (null, если не выбрано)
        val selectedAnswers = mutableMapOf<Int, Int?>()
    }

    // Метод для инициализация структуры для хранения выбранных ответов
    private fun initSelectedAnswers() {
        for(i in 0..<QuestionsRepository.questions.size) {
            selectedAnswers[i] = null // Изначально ответы не выбраны
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}