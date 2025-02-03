package com.example.hw5_coroutines_compose

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hw5_coroutines_compose.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    private var viewBinding: FragmentMainBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentMainBinding.inflate(inflater, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding?.composeContainerId?.let { composeView ->
            (composeView).setContent {
                CoroutinesScreen()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}



