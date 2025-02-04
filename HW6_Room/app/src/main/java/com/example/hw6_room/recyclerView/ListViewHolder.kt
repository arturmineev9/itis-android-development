package com.example.hw6_room.recyclerView

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.hw6_room.databinding.ListViewHolderBinding

open class ListViewHolder(
    private val viewBinding: ListViewHolderBinding,
    private val glide: RequestManager
) : RecyclerView.ViewHolder(viewBinding.root) {

    fun onBind(){
        viewBinding.run{


        }
    }
}