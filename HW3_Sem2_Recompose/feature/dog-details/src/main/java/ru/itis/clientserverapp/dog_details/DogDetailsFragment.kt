package ru.itis.clientserverapp.dog_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.itis.clientserverapp.base_feature.BaseFragment
import ru.itis.clientserverapp.dog_details.compose.DogDetailsScreen
import ru.itis.clientserverapp.dog_details.constants.DogDetailsConstants
import ru.itis.clientserverapp.dog_details.databinding.FragmentDogDetailsBinding
import ru.itis.clientserverapp.dog_details.viewmodel.DogDetailsViewModel


@AndroidEntryPoint
class DogDetailsFragment : BaseFragment(R.layout.fragment_dog_details) {

    private var viewBinding: FragmentDogDetailsBinding? = null
    private val viewModel: DogDetailsViewModel by viewModels()
    private val dogId by lazy { arguments?.getString(DogDetailsConstants.DOG_ID) ?: "" }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                DogDetailsScreen(
                    viewModel = viewModel
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadDogDetails(dogId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}
