package ru.itis.clientserverapp.graph_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import ru.itis.clientserverapp.base_feature.BaseFragment
import ru.itis.clientserverapp.graph_screen.compose.GraphScreen
import ru.itis.clientserverapp.graph_screen.viewmodel.GraphViewModel
import kotlin.getValue


class GraphFragment : BaseFragment(R.layout.fragment_graph) {

    private val viewModel: GraphViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                GraphScreen(
                    viewModel = viewModel
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
