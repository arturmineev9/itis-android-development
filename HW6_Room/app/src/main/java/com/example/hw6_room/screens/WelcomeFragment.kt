package com.example.hw6_room.screens

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.hw6_room.R
import com.example.hw6_room.databinding.FragmentWelcomeBinding


class WelcomeFragment : Fragment() {

    private var viewBinding: FragmentWelcomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initVideoView()
        initButtons()
    }

    private fun initButtons() {
        viewBinding?.run {
            entryScreenButton.setOnClickListener {
                findNavController().navigate(R.id.action_welcomeFragment_to_loginFragment)
            }

            registrationScreenButton.setOnClickListener {
                findNavController().navigate(R.id.action_welcomeFragment_to_registrationFragment)
            }
        }
    }
    private fun initVideoView() {
        viewBinding?.run {
            val videoPath = "android.resource://${requireContext().packageName}/${R.raw.rock}"
            val videoUri = Uri.parse(videoPath)
            videoView.setVideoURI(videoUri)
            videoView.setOnCompletionListener {
                videoView.start()
            }
            videoView.start()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}