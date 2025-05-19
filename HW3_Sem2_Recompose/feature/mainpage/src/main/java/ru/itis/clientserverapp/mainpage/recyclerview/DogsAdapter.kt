package ru.itis.clientserverapp.mainpage.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import ru.itis.clientserverapp.domain.models.DogModel
import ru.itis.clientserverapp.mainpage.R

class DogsAdapter(
    private val glide: RequestManager,
    private val onItemClick: (DogModel) -> Unit
) : RecyclerView.Adapter<DogsViewHolder>() {

    private val dogs = mutableListOf<DogModel>()

    fun submitList(newDogs: List<DogModel>) {
        dogs.clear()
        dogs.addAll(newDogs)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dog, parent, false)
        return DogsViewHolder(view, glide)
    }

    override fun onBindViewHolder(holder: DogsViewHolder, position: Int) {
        holder.bind(dogs[position], onItemClick)
    }

    override fun getItemCount() = dogs.size
}
