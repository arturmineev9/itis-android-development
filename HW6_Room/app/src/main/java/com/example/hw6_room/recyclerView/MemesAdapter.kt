package com.example.hw6_room.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.hw6_room.databinding.MemeViewHolderBinding
import com.example.hw6_room.db.entity.MemeEntity

class MemesAdapter(
    private var list: MutableList<MemeEntity>,
    private val requestManager: RequestManager,
    private val onItemClick: (Int) -> Unit,
    private val onItemLongClick: (MemeEntity, Int) -> Unit,
    private val onFavoriteClick: (Int, Boolean) -> Unit
) : RecyclerView.Adapter<MemesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemesViewHolder {
        return MemesViewHolder(
            MemeViewHolderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            glide = requestManager,
            onFavoriteClick
        )
    }


    override fun getItemCount(): Int {
        return list.size
    }

    fun removeItem(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }


    fun updateItem(position: Int, meme: MemeEntity) {
        list[position - 1] = meme
        notifyItemChanged(position - 1)
    }

    override fun onBindViewHolder(holder: MemesViewHolder, position: Int) {
        val meme = list[position]
        holder.bindItem(meme = meme)

        holder.itemView.setOnLongClickListener {
            onItemLongClick(meme, position)
            true
        }

        holder.itemView.setOnClickListener {
            onItemClick(meme.id)
        }
    }
}