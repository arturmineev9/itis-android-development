package com.example.hw2_recyclerview.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.hw2_recyclerview.databinding.ItemHolderListTypeBinding
import com.example.hw2_recyclerview.model.ViewHolderData

class ListTypeViewHolder(
    private val viewBinding: ItemHolderListTypeBinding,
    private val requestManager: RequestManager,
) : RecyclerView.ViewHolder(viewBinding.root) {

    fun bindItem(itemData : ViewHolderData) {
        with(viewBinding) {
            listHeaderTv.text = itemData.headerText
            requestManager.load(itemData.imageUrl)
                .into(listIv)
        }
    }
}