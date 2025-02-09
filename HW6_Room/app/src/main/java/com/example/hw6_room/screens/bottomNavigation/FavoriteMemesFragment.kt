package com.example.hw6_room.screens.bottomNavigation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.hw6_room.MainActivity
import com.example.hw6_room.R
import com.example.hw6_room.databinding.FragmentFavoriteMemesBinding
import com.example.hw6_room.db.entity.MemeEntity
import com.example.hw6_room.di.ServiceLocator
import com.example.hw6_room.recyclerView.MemesAdapter
import com.example.hw6_room.utils.Constants
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

            if (favoriteMemesList.isEmpty()) {
                viewBinding?.noFavoriteMemesMessage?.visibility = View.VISIBLE
                viewBinding?.favoriteRecycler?.visibility = View.GONE
            } else {
                viewBinding?.noFavoriteMemesMessage?.visibility = View.GONE
                viewBinding?.favoriteRecycler?.visibility = View.VISIBLE
            }

            memesAdapter = MemesAdapter(
                list = favoriteMemesList,
                requestManager = requestManager,
                onItemClick = { memeId ->
                    openMemeDetails(memeId)
                },
                onItemLongClick = { meme, position ->
                    toggleFavorite(meme, position)
                },
                onFavoriteClick = { position, _ ->
                    if (position in favoriteMemesList.indices) {
                        toggleFavorite(favoriteMemesList[position], position)
                    } else {
                        val errorMessage = getString(R.string.invalid_meme_index, position, favoriteMemesList.size)
                        Log.e(Constants.FAVORITE_MEMES_FRAGMENT_TAG, errorMessage)
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
            memesAdapter?.updateItem(position, updatedMeme)
        }
    }

    private fun openMemeDetails(memeId: Int) {
        val bundle = Bundle().apply {
            putInt(Constants.MEME_DETAILS_BUNDLE_KEY, memeId)
        }
        findNavController().navigate(R.id.action_favoriteMemesFragment_to_memeDetailsFragment, bundle)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}
