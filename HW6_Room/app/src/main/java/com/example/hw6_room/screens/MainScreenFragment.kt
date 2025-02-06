package com.example.hw6_room.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.hw6_room.MainActivity
import com.example.hw6_room.R
import com.example.hw6_room.databinding.FragmentMainScreenBinding
import com.example.hw6_room.recyclerView.MemesAdapter
import com.example.hw6_room.recyclerView.MemesRepository


class MainScreenFragment : Fragment() {

    private var viewBinding: FragmentMainScreenBinding? = null
    private var memesAdapter: MemesAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentMainScreenBinding.inflate(inflater, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setBottomNavigationVisibility(true)
        val glide = Glide.with(requireContext())
        initRecyclerView(glide)
    }

    private fun initRecyclerView(requestManager: RequestManager) {
        val memesList = MemesRepository.getMemesList()
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


    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }



}