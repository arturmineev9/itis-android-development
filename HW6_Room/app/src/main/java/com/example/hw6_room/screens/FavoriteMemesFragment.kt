package com.example.hw6_room.screens

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.hw6_room.MainActivity
import com.example.hw6_room.databinding.FragmentFavoriteMemesBinding
import com.example.hw6_room.db.entity.MemeEntity
import com.example.hw6_room.di.ServiceLocator
import com.example.hw6_room.recyclerView.MemesAdapter
import com.example.hw6_room.utils.SessionManager
import kotlinx.coroutines.launch

class FavoriteMemesFragment : Fragment() {

    private var viewBinding: FragmentFavoriteMemesBinding? = null
    private var memesAdapter: MemesAdapter? = null
    private val memeRepository = ServiceLocator.getMemeRepository()
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentFavoriteMemesBinding.inflate(inflater, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(requireContext())
        (activity as MainActivity).setBottomNavigationVisibility(true)
        val glide = Glide.with(requireContext())
        initRecyclerView(glide)
    }

    private fun initRecyclerView(requestManager: RequestManager) {
        lifecycleScope.launch {
            val userId = sessionManager.getUserId()
            val favoriteMemesList = memeRepository.getFavoriteMemes(userId).toMutableList()
            memesAdapter = MemesAdapter(
                list = favoriteMemesList,
                requestManager = requestManager,
                onItemLongClick = { meme, position ->
                    toggleFavorite(meme, position)
                },

                onFavoriteClick = { position, isFavorite ->
                    if (position in favoriteMemesList.indices) {
                        toggleFavorite(favoriteMemesList[position], position)
                    } else {
                        Log.e("FavoriteMemesFragment", "Неверный индекс: $position, размер списка: ${favoriteMemesList.size}")
                    }
                }

            )
            viewBinding?.run {
                favoriteRecycler.adapter = memesAdapter
                favoriteRecycler.layoutManager = GridLayoutManager(requireContext(), 2)
            }
        }
    }

    private fun toggleFavorite(meme: MemeEntity, position: Int) {
        lifecycleScope.launch {
            val updatedMeme = meme.copy(isFavorite = !meme.isFavorite)
            memeRepository.toggleFavorite(updatedMeme.id, updatedMeme.isFavorite)

            // Загружаем заново список избранных мемов
            val userId = sessionManager.getUserId()
            val updatedMemesList = memeRepository.getFavoriteMemes(userId).toMutableList()

            memesAdapter?.updateList(updatedMemesList)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}
