package com.example.hw2_recyclerview.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.hw2_recyclerview.model.MultipleHoldersData
import com.example.hw2_recyclerview.model.ViewHolderData

class RecyclerViewDiffUtil(
    private val oldList: List<MultipleHoldersData>,
    private val newList: List<MultipleHoldersData>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return if (oldItem is ViewHolderData && newItem is ViewHolderData) {
            (oldItem.imageUrl == newItem.imageUrl &&
                    oldItem.headerText == newItem.headerText
                    && oldItem.description == newItem.description)
        } else false
    }
}