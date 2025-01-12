package com.example.hw2_recyclerview.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.hw2_recyclerview.databinding.ItemHolderGridTypeBinding
import com.example.hw2_recyclerview.model.ViewHolderData

class GridTypeViewHolder(
    private val viewBinding: ItemHolderGridTypeBinding,
    private val requestManager: RequestManager,
    private val onItemClick: (Int) -> Unit,
    private val onLongItemClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(viewBinding.root) {

    init {
        viewBinding.run {
            root.setOnClickListener {
                onItemClick.invoke(adapterPosition)
            }

            root.setOnLongClickListener {
                onLongItemClick.invoke(adapterPosition)
                true
            }
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