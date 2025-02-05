package com.example.hw6_room.recyclerView

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.hw6_room.MemeEntity
import com.example.hw6_room.databinding.MemeViewHolderBinding

open class MemesViewHolder(
    private val viewBinding: MemeViewHolderBinding,
    private val glide: RequestManager
) : RecyclerView.ViewHolder(viewBinding.root) {

    fun bindItem(itemData: MemeEntity){
        viewBinding.run{
            gridHeaderTv.text = itemData.description

            glide.load(itemData.imageUrl)
                .into(gridIv)
        }
    }
}