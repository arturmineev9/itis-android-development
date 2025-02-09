package com.example.hw6_room.screens.entry

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.hw6_room.MainActivity
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
        (activity as MainActivity).setBottomNavigationVisibility(false)
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

            videoView.setOnPreparedListener { mp ->
                mp.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING)
                videoView.start()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}