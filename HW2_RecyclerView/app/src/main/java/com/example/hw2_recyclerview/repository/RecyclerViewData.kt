package com.example.hw2_recyclerview.repository

import com.example.hw2_recyclerview.model.MultipleHoldersData
import com.example.hw2_recyclerview.repository.RecyclerViewRepository.items
import com.example.hw2_recyclerview.utils.RvTypes

object RecyclerViewData {

    //var isGridMode : Boolean = false
    var recyclerViewType = RvTypes.LIST
    var recyclerViewList = mutableListOf<MultipleHoldersData>()

    init {
        recyclerViewList.addAll(items.take(25))
    }

    fun setGridMode() {
        recyclerViewType = RvTypes.GRID
    }

    fun setListMode() {
        recyclerViewType = RvTypes.LIST
    }

    fun setModifiedGridMode() {
        recyclerViewType = RvTypes.MODIFIED_GRID
    }

}