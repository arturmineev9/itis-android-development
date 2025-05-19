package ru.itis.clientserverapp.mainpage.recyclerview

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import ru.itis.clientserverapp.domain.models.DogModel
import ru.itis.clientserverapp.mainpage.R

class DogsViewHolder(
    itemView: View,
    private val glide: RequestManager
) : RecyclerView.ViewHolder(itemView) {
    private val ivDog: ImageView = itemView.findViewById(R.id.ivDog)
    private val tvBreed: TextView = itemView.findViewById(R.id.tvBreed)

    fun bind(dog: DogModel, onClick: (DogModel) -> Unit) {
        glide.load(dog.url)
            .into(ivDog)

        tvBreed.text = dog.breed.name.takeIf { it.isNotBlank() }

        itemView.setOnClickListener {
            onClick(dog)
        }
    }
}
