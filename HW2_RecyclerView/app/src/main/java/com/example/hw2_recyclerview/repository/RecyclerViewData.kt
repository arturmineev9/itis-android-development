package com.example.hw2_recyclerview.repository

import com.example.hw2_recyclerview.model.MultipleHoldersData
import com.example.hw2_recyclerview.repository.RecyclerViewRepository.items

object RecyclerViewData {

    var isGridMode : Boolean = false
    var recyclerViewList = mutableListOf<MultipleHoldersData>()

    init {
        recyclerViewList.addAll(items.take(25))
    }

    fun setGridMode() {
        isGridMode = true
    }

    fun setListMode() {
        isGridMode = false
    }

}