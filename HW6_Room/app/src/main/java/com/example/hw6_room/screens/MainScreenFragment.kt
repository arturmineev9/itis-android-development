package com.example.hw6_room.screens

import android.os.Bundle
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
import com.example.hw6_room.databinding.FragmentMainScreenBinding
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
            val memesList = memeRepository.getUserMemes(userId)
            memesAdapter = MemesAdapter(
                list = memesList,
                requestManager = requestManager
            )
            viewBinding?.run {
                mainRecycler.adapter = memesAdapter
                mainRecycler.layoutManager =
                    GridLayoutManager(requireContext(), 2)
            }
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