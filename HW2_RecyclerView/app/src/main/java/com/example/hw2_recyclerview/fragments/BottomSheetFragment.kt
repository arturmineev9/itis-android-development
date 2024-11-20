package com.example.hw2_recyclerview.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.hw2_recyclerview.R
import com.example.hw2_recyclerview.adapter.AdapterWithMultipleHolders
import com.example.hw2_recyclerview.databinding.DialogBottomSheetBinding
import com.example.hw2_recyclerview.repository.RecyclerViewData
import com.example.hw2_recyclerview.repository.RecyclerViewRepository
import com.example.hw2_recyclerview.repository.RecyclerViewRepository.items
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment(
    private var adapter: AdapterWithMultipleHolders?
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
            addRandom.setOnClickListener {
                adapter?.addRandomElement()
                // Отображение размера списка
                Toast.makeText(
                    requireContext(),
                    adapter?.dataList?.size.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }

            deleteRandom.setOnClickListener {
                adapter?.deleteRandomElement()
                Toast.makeText(
                    requireContext(),
                    adapter?.dataList?.size.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}