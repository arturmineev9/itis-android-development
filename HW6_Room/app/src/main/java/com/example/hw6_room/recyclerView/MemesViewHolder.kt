package com.example.hw6_room.recyclerView

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.hw6_room.databinding.MemeViewHolderBinding
import com.example.hw6_room.db.entity.MemeEntity

open class MemesViewHolder(
    private val viewBinding: MemeViewHolderBinding,
    private val glide: RequestManager
) : RecyclerView.ViewHolder(viewBinding.root) {

    fun bindItem(itemData: MemeEntity){
        viewBinding.run{
            gridHeaderTv.text = itemData.description
            Log.d("url", itemData.url)
            glide.load(itemData.url)
                .into(gridIv)
        }
    }
}