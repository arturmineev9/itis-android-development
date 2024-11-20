package com.example.hw2_recyclerview.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hw2_recyclerview.R
import com.example.hw2_recyclerview.adapter.AdapterWithMultipleHolders
import com.example.hw2_recyclerview.adapter.AdapterWithMultipleHolders.Companion.VIEW_TYPE_BUTTON
import com.example.hw2_recyclerview.databinding.FragmentFirstScreenBinding
import com.example.hw2_recyclerview.model.MultipleHoldersData
import com.example.hw2_recyclerview.repository.RecyclerViewData
import com.example.hw2_recyclerview.repository.RecyclerViewRepository


class FirstScreenFragment : Fragment() {

    private var viewBinding: FragmentFirstScreenBinding? = null
    private var rvAdapter: AdapterWithMultipleHolders? = null
    private val mainContainerId = R.id.main_fragment_container

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        showBottomSheet()
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
            onItemClick = ::onItemClick,
            items = RecyclerViewData.recyclerViewList
        )

        viewBinding?.let { binding ->
            binding.mainRecycler.adapter = rvAdapter
            binding.mainRecycler.layoutManager = if (RecyclerViewData.isGridMode) {
                getGridLayout()
            } else {
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            }
        }

    }

    private fun onListButtonClick() {
        viewBinding?.mainRecycler?.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        rvAdapter?.setListMode()
    }

    private fun getGridLayout() : GridLayoutManager {
        val gridLayoutManager = GridLayoutManager(context, 3)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (rvAdapter?.getItemViewType(position)) {
                    VIEW_TYPE_BUTTON -> gridLayoutManager.spanCount
                    else -> 1
                }
            }
        }
    return gridLayoutManager

    }
    private fun onGridButtonClick() {
        val gridLayoutManager = getGridLayout()
        viewBinding?.mainRecycler?.layoutManager = gridLayoutManager
        rvAdapter?.setGridMode()
        Log.d("Adapter", rvAdapter?.isGridMode.toString())
        Log.d("RVData", RecyclerViewData.isGridMode.toString())
    }

    private fun onItemClick(position: Int) {
        val itemId = rvAdapter?.dataList?.get(position)?.id
        val existingFragment =
            parentFragmentManager.findFragmentByTag(SecondScreenFragment.TAG)

        if (existingFragment is SecondScreenFragment) {
            existingFragment.updateData(itemId.toString())
        } else {
            val secondScreenFragment = SecondScreenFragment.newInstance(itemId.toString())
            parentFragmentManager.beginTransaction()
                .replace(mainContainerId, secondScreenFragment, SecondScreenFragment.TAG)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun showBottomSheet() {
        viewBinding?.run {
            fab.setOnClickListener {
                val bottomSheet = BottomSheetFragment(rvAdapter)
                bottomSheet.show(parentFragmentManager, "MyBottomSheetDialog")
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}
