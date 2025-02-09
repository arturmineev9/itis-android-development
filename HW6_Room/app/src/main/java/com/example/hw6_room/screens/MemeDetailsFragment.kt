package com.example.hw6_room.screens

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.hw6_room.MainActivity
import com.example.hw6_room.R
import com.example.hw6_room.databinding.FragmentMemeDetailsBinding
import com.example.hw6_room.db.repository.MemeRepository
import com.example.hw6_room.di.ServiceLocator
import com.example.hw6_room.utils.Constants
import kotlinx.coroutines.launch

class MemeDetailsFragment : Fragment() {

    private var viewBinding: FragmentMemeDetailsBinding? = null
    private var memeId: Int = -1
    private lateinit var memeRepository: MemeRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            memeId = it.getInt(Constants.MEME_DETAILS_BUNDLE_KEY)
        }
        memeRepository = ServiceLocator.getMemeRepository()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentMemeDetailsBinding.inflate(inflater, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setBottomNavigationVisibility(false)
        loadMemeDetails()
    }

    private fun loadMemeDetails() {
        lifecycleScope.launch {
            val meme = memeRepository.getMemeById(memeId)
            meme?.let {
                viewBinding?.run {
                    detailsTitle.text = it.title
                    Glide.with(requireContext())
                        .load(it.url)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(detailsImage)

                    detailsDescription.text = it.description

                    fabShare.setOnClickListener {
                        shareMeme(meme.url)
                    }

                }
            }
        }
    }

    private fun shareMeme(imageUri: String) {
        val intent = Intent(Intent.ACTION_SEND)

        if (imageUri.startsWith(Constants.HTTPS)) {
            intent.apply {
                type = Constants.TEXT_TYPE
                putExtra(Intent.EXTRA_TEXT, imageUri)
            }
        } else {
            val uri = Uri.parse(imageUri)
            intent.apply {
                type = Constants.IMAGE_TYPE
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
        }

        startActivity(Intent.createChooser(intent, getString(R.string.share_meme_title)))
    }



    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}
