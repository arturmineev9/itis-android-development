package com.example.hw2_recyclerview.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.example.hw2_recyclerview.adapter.AdapterWithMultipleHolders
import com.example.hw2_recyclerview.databinding.DialogBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment(
    private var adapter: AdapterWithMultipleHolders? // Принимает адаптер, чтобы манипулировать данными в RecyclerView
) : BottomSheetDialogFragment() {

    private var viewBinding: DialogBottomSheetBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = DialogBottomSheetBinding.inflate(inflater, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding?.run {

            // Логика изменения кнопок в зависимости от ввода в текстовое поле
            editText.doOnTextChanged { text, _, _, _ ->
                addElements.isEnabled = text?.isNotEmpty() == true
                deleteElements.isEnabled = text?.isNotEmpty() == true
            }

            // Кнопка для добавления случайного элемента
            addRandom.setOnClickListener {
                adapter?.addRandomElement(requireContext())
            }

            // Кнопка для удаления случайного элемента
            deleteRandom.setOnClickListener {
                adapter?.deleteRandomElement(requireContext())
            }

            // Кнопка для добавления нескольких элементов, введенных в поле
            addElements.setOnClickListener {
                val enteredNumber = editText.text.toString().toIntOrNull()
                if (enteredNumber != null && enteredNumber > 0) {
                    adapter?.addElements(
                        requireContext(),
                        enteredNumber
                    ) // Если введено корректное число, добавляем элементы
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Ошибка! Некорректное значение в строке ввода.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            // Кнопка для удаления нескольких элементов, введенных в поле
            deleteElements.setOnClickListener {
                val enteredNumber = editText.text.toString().toIntOrNull()
                if (enteredNumber != null && enteredNumber > 0) {
                    adapter?.deleteElements(
                        requireContext(),
                        enteredNumber
                    ) // Если введено корректное число, удаляем элементы
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Ошибка! Некорректное значение в строке ввода.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}