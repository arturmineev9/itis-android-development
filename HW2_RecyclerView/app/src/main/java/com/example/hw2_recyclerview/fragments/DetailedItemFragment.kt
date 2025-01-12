package com.example.hw2_recyclerview.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.hw2_recyclerview.databinding.FragmentSecondScreenBinding
import com.example.hw2_recyclerview.model.ViewHolderData
import com.example.hw2_recyclerview.repository.TanksRepository


class DetailedItemFragment : Fragment() {

    private var viewBinding: FragmentSecondScreenBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentSecondScreenBinding.inflate(inflater, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Получаем ID элемента, переданный через начальный фрагмент
        val itemId = requireArguments().getString(ARG_ITEM_ID)
        // Загружаем данные элемента с этим ID
        inflateView(itemId.toString())
    }
    // Метод для отрисовки изображения
    private fun inflateView(itemId: String) {
        // Получаем элемент из репозитория по ID
        val item = TanksRepository.getItemById(itemId)
        item?.let {
            viewBinding?.run {
                val viewHolderItem = (it as? ViewHolderData) // Каст от MultipleHoldersData к ViewHolderData
                Glide.with(requireContext()).load(viewHolderItem?.imageUrl).into(imageView)
                header.text = viewHolderItem?.headerText
                description.text = viewHolderItem?.description
            }
        }
    }
    // Метод для обновления данных фрагмента, если экземпляр фрагмента уже существует
    fun updateData(itemId: String) {
        inflateView(itemId)
    }

    companion object {
        const val TAG = "SECOND_FRAGMENT"
        private const val ARG_ITEM_ID = "arg_item_id"
        // Метод для создания нового экземпляра фрагмента с передачей ID элемента
        fun newInstance(itemId: String) = DetailedItemFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_ITEM_ID, itemId)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}