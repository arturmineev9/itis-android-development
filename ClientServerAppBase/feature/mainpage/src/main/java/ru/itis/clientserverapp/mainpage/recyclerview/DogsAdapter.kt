package ru.itis.clientserverapp.mainpage.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.itis.clientserverapp.domain.models.DogModel
import ru.itis.clientserverapp.mainpage.R

class DogsAdapter(
    private val onItemClick: (DogModel) -> Unit
): RecyclerView.Adapter<DogsAdapter.DogViewHolder>() {

    private val dogs = mutableListOf<DogModel>()

    fun submitList(newDogs: List<DogModel>) {
        dogs.clear()
        dogs.addAll(newDogs)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dog, parent, false)
        return DogViewHolder(view)
    }

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        holder.bind(dogs[position], onItemClick)
    }

    override fun getItemCount() = dogs.size

    class DogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivDog: ImageView = itemView.findViewById(R.id.ivDog)
        private val tvBreed: TextView = itemView.findViewById(R.id.tvBreed)

        fun bind(dog: DogModel, onClick: (DogModel) -> Unit) {
            Glide.with(itemView.context)
                .load(dog.url)
                .into(ivDog)

            tvBreed.text = dog.breed.name.takeIf { it.isNotBlank() }

            itemView.setOnClickListener {
                onClick(dog)
            }
        }
    }
}