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
    private val onItemLongClick: (MemeEntity, Int) -> Unit
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

    // Метод для удаления элемента из списка
    fun removeItem(position: Int) {
        list.removeAt(position)  // Удаляем элемент из списка
        notifyItemRemoved(position)  // Уведомляем адаптер об удалении элемента
    }

    override fun onBindViewHolder(holder: MemesViewHolder, position: Int) {
        holder.bindItem(itemData = list[position])

        holder.itemView.setOnLongClickListener {
            onItemLongClick(list[position], position)  // Передаем и мем, и позицию
            true
        }
    }
}