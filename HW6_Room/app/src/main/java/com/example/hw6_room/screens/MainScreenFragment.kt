package com.example.hw6_room.screens

import android.os.Bundle
import android.util.Log
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
import com.example.hw6_room.db.repository.MemeRepository
import com.example.hw6_room.di.ServiceLocator
import com.example.hw6_room.recyclerView.MemesAdapter
import com.example.hw6_room.recyclerView.MemesRepository
import com.example.hw6_room.utils.SessionManager
import kotlinx.coroutines.launch


class MainScreenFragment : Fragment() {

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
                onItemLongClick = { meme, position ->
                    showDeleteConfirmationDialog(meme, position)
                },
                onFavoriteClick = { position, isFavorite ->
                    Log.d("position", position.toString())
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
            memesAdapter?.removeItem(position)  // Удаляем элемент из списка адаптера
        }
    }

    private fun showDeleteConfirmationDialog(meme: MemeEntity, position: Int) {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Подтверждение удаления")
            .setMessage("Вы уверены, что хотите удалить этот мем?")
            .setPositiveButton("Удалить") { _, _ ->
                deleteMeme(meme, position)  // Если пользователь нажимает "Удалить", то вызываем метод удаления
            }
            .setNegativeButton("Отмена", null)  // Если пользователь нажимает "Отмена", просто закрываем диалог
            .create()

        dialog.show()
    }


    private fun toggleFavorite(meme: MemeEntity, position: Int) {
        lifecycleScope.launch {
            val updatedMeme = meme.copy(isFavorite = !meme.isFavorite) // Создаём новую копию
            memeRepository.toggleFavorite(updatedMeme.id, updatedMeme.isFavorite)
            memesAdapter?.updateItem(position, updatedMeme)
        }
    }


    private fun initFab() {
        viewBinding?.run {
            fab.setOnClickListener {
                findNavController().navigate(R.id.action_mainScreenFragment_to_addContentFragment)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }



}