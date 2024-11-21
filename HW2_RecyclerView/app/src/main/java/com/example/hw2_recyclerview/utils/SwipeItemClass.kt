package com.example.hw2_recyclerview.utils

import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.hw2_recyclerview.adapter.AdapterWithMultipleHolders
import com.example.hw2_recyclerview.repository.RecyclerViewData

class SwipeItemClass(
    private var rvAdapter: AdapterWithMultipleHolders
) {
    val itemTouchHelper =
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val position = viewHolder.adapterPosition
                Log.d("tag", position.toString() + " " + RecyclerViewData.recyclerViewList.size.toString())
                if (position in 1..<RecyclerViewData.recyclerViewList.size) {
                    RecyclerViewData.recyclerViewList.removeAt(position)
                    rvAdapter.dataList = RecyclerViewData.recyclerViewList
                    rvAdapter.notifyItemRemoved(position)

                } else {
                    // Восстановление элемента, если индекс некорректен
                    rvAdapter.notifyItemChanged(position)
                }
            }

            override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
                return 0.66f
            }

            override fun isItemViewSwipeEnabled(): Boolean {
                return RecyclerViewData.recyclerViewType == RvTypes.LIST
            }
        })
}