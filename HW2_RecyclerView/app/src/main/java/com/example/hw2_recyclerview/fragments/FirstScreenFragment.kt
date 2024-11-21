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
import com.example.hw2_recyclerview.utils.RvTypes
import com.example.hw2_recyclerview.utils.SwipeItemClass


class FirstScreenFragment : Fragment() {

    private var viewBinding: FragmentFirstScreenBinding? = null
    private var rvAdapter: AdapterWithMultipleHolders? = null
    private val mainContainerId = R.id.main_fragment_container

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        showBottomSheet()
        setupSwipeToDelete(viewBinding?.mainRecycler)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentFirstScreenBinding.inflate(inflater, container, false)
        return viewBinding?.root
    }
    private fun setupSwipeToDelete(recyclerView : RecyclerView?) {
        if (recyclerView?.layoutManager is LinearLayoutManager) {
            val swipeItemClass = rvAdapter?.let { SwipeItemClass(it) }
            swipeItemClass?.itemTouchHelper?.attachToRecyclerView(recyclerView)
        }
    }

    private fun disableSwipe() {
        val swipeItemClass = rvAdapter?.let { SwipeItemClass(it) }
        swipeItemClass?.itemTouchHelper?.attachToRecyclerView(null)
    }
    private fun initRecyclerView() {
        rvAdapter = AdapterWithMultipleHolders(
            requestManager = Glide.with(requireContext()),
            onGridButtonCLick = ::onGridButtonClick,
            onListButtonCLick = ::onListButtonClick,
            onModifiedGridButtonCLick = ::onModifiedGridButtonClick,
            onItemClick = ::onItemClick,
            items = RecyclerViewData.recyclerViewList
        )

        viewBinding?.let { binding ->
            binding.mainRecycler.adapter = rvAdapter
            binding.mainRecycler.layoutManager = when (RecyclerViewData.recyclerViewType){
                RvTypes.LIST -> LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                RvTypes.GRID -> getGridLayout()
                RvTypes.MODIFIED_GRID -> getModifiedGridLayoutManager()
            }
        }

    }

    private fun onListButtonClick() {
        viewBinding?.mainRecycler?.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        rvAdapter?.setListMode()
        setupSwipeToDelete(viewBinding?.mainRecycler)
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

    private fun getModifiedGridLayoutManager() : GridLayoutManager {
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)


        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                if (position == 0) {
                    return 2
                }

                return when ((position - 1) % 4) {
                    0, 3 -> 2
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
        disableSwipe()
    }

    private fun onModifiedGridButtonClick() {
        val modifiedGridLayoutManager = getModifiedGridLayoutManager()
        viewBinding?.mainRecycler?.layoutManager = modifiedGridLayoutManager
        rvAdapter?.setModifiedGridMode()
        disableSwipe()
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
