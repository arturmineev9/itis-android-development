package com.example.hw2_recyclerview.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hw2_recyclerview.R
import com.example.hw2_recyclerview.adapter.AdapterWithMultipleHolders
import com.example.hw2_recyclerview.adapter.AdapterWithMultipleHolders.Companion.VIEW_TYPE_BUTTON
import com.example.hw2_recyclerview.databinding.FragmentFirstScreenBinding
import com.example.hw2_recyclerview.repository.RecyclerViewRepository


class FirstScreenFragment : Fragment() {

    private var viewBinding: FragmentFirstScreenBinding? = null
    private var rvAdapter: AdapterWithMultipleHolders? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewBinding = FragmentFirstScreenBinding.inflate(inflater, container, false)
        return viewBinding?.root
    }

    private fun initRecyclerView() {

        rvAdapter = AdapterWithMultipleHolders(
            requestManager = Glide.with(requireContext()),
            onGridButtonCLick = ::onGridButtonClick,
            onListButtonCLick = ::onListButtonClick,
            items = RecyclerViewRepository.getListForMultipleTypes()
        )

        // Проверка на null для viewBinding и rvAdapter
        viewBinding?.let { binding ->
            binding.mainRecycler.adapter = rvAdapter
            binding.mainRecycler.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
    }


    private fun onListButtonClick() {
        rvAdapter?.setListMode()
        viewBinding?.mainRecycler?.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        rvAdapter?.notifyDataSetChanged()
    }

    private fun onGridButtonClick() {
        rvAdapter?.setGridMode()
        val gridLayoutManager = GridLayoutManager(context, 3)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (rvAdapter?.getItemViewType(position)) {
                    VIEW_TYPE_BUTTON -> gridLayoutManager.spanCount
                    else -> 1
                }
            }
        }

        viewBinding?.mainRecycler?.layoutManager = gridLayoutManager
        rvAdapter?.notifyDataSetChanged()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}
