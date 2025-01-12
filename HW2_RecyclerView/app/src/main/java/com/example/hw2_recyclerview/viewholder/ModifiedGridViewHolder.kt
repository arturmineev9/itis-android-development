package com.example.hw2_recyclerview.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.hw2_recyclerview.databinding.ItemHolderGridModifiedBinding
import com.example.hw2_recyclerview.databinding.ItemHolderListTypeBinding
import com.example.hw2_recyclerview.model.ViewHolderData

class ModifiedGridViewHolder(
    private val viewBinding: ItemHolderGridModifiedBinding,
    private val requestManager: RequestManager,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.ViewHolder(viewBinding.root) {


    init {
        viewBinding.root.setOnClickListener {
            onItemClick.invoke(adapterPosition)
        }
    }

    fun bindItem(itemData : ViewHolderData) {
        with(viewBinding) {
            gridHeaderTv.text = itemData.headerText
            requestManager.load(itemData.imageUrl)
                .into(gridIv)
        }
    }


}