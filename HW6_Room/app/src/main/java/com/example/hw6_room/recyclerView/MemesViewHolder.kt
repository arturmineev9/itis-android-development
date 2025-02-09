package com.example.hw6_room.recyclerView

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.hw6_room.R
import com.example.hw6_room.databinding.MemeViewHolderBinding
import com.example.hw6_room.db.entity.MemeEntity

open class MemesViewHolder(
    private val viewBinding: MemeViewHolderBinding,
    private val glide: RequestManager,
    private val onFavoriteClick: (Int, Boolean) -> Unit
) : RecyclerView.ViewHolder(viewBinding.root) {

    fun bindItem(meme: MemeEntity) {
        viewBinding.run {
            gridHeaderTv.text = meme.title
            glide.load(meme.url)
                .into(gridIv)

            btnFavorite.setImageResource(
                if (meme.isFavorite) R.drawable.ic_heart_fill else R.drawable.ic_heart_empty
            )

            btnFavorite.setOnClickListener {
                onFavoriteClick(meme.id, !meme.isFavorite)
            }
        }
    }
}