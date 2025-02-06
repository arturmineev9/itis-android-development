package com.example.hw6_room.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.hw6_room.databinding.MemeViewHolderBinding
import com.example.hw6_room.db.entity.MemeEntity

class MemesAdapter(
    private var list: List<MemeEntity>,
    private val requestManager: RequestManager
) : RecyclerView.Adapter<MemesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemesViewHolder {
        return MemesViewHolder(
            MemeViewHolderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            glide = requestManager,
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MemesViewHolder, position: Int) {
        holder.bindItem(itemData = list[position])
    }
}