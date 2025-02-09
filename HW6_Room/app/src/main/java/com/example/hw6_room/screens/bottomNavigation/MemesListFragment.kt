package com.example.hw6_room.screens.bottomNavigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.hw6_room.MainActivity
import com.example.hw6_room.R
import com.example.hw6_room.databinding.FragmentMainScreenBinding
import com.example.hw6_room.db.entity.MemeEntity
import com.example.hw6_room.di.ServiceLocator
import com.example.hw6_room.recyclerView.MemesAdapter
import com.example.hw6_room.utils.Constants
import com.example.hw6_room.utils.SessionManager
import kotlinx.coroutines.launch


class MemesListFragment : Fragment() {

    private var viewBinding: FragmentMainScreenBinding? = null
    private var memesAdapter: MemesAdapter? = null
    private val memeRepository = ServiceLocator.getMemeRepository()
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentMainScreenBinding.inflate(inflater, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(requireContext())
        (activity as MainActivity).setBottomNavigationVisibility(true)
        val glide = Glide.with(requireContext())
        initRecyclerView(glide)
        initFab()
    }

    private fun initRecyclerView(requestManager: RequestManager) {
        lifecycleScope.launch {
            val userId = sessionManager.getUserId()
            val memesList = memeRepository.getUserMemes(userId).toMutableList()
            memesAdapter = MemesAdapter(
                list = memesList,
                requestManager = requestManager,
                onItemClick = { memeId ->
                    openMemeDetails(memeId)
                },
                onItemLongClick = { meme, position ->
                    showDeleteConfirmationDialog(meme, position)
                },
                onFavoriteClick = { position, _ ->
                    toggleFavorite(memesList[position - 1], position)
                }
            )
            viewBinding?.run {
                mainRecycler.adapter = memesAdapter
                mainRecycler.layoutManager = GridLayoutManager(requireContext(), 2)
            }
        }
    }

    private fun deleteMeme(meme: MemeEntity, position: Int) {
        lifecycleScope.launch {
            memeRepository.deleteMeme(meme.id, sessionManager.getUserId())
            memesAdapter?.removeItem(position)
        }
    }

    private fun showDeleteConfirmationDialog(meme: MemeEntity, position: Int) {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.delete_confirmation_title))
            .setMessage(getString(R.string.delete_confirmation_message))
            .setPositiveButton(getString(R.string.delete_button)) { _, _ ->
                deleteMeme(meme, position)
            }
            .setNegativeButton(getString(R.string.cancel_button), null)
            .create()

        dialog.show()
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
        findNavController().navigate(R.id.action_memesListFragment_to_memeDetailsFragment, bundle)
    }


    private fun initFab() {
        viewBinding?.run {
            fab.setOnClickListener {
                findNavController().navigate(R.id.action_memesListFragment_to_addContentFragment)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }



}