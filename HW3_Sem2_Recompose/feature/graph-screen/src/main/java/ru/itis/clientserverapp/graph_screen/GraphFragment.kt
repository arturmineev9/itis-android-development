package ru.itis.clientserverapp.graph_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import ru.itis.clientserverapp.base_feature.BaseFragment
import ru.itis.clientserverapp.graph_screen.compose.GraphScreen


class GraphFragment : BaseFragment(R.layout.fragment_graph) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                GraphScreen()
            }
        }
    }
}
