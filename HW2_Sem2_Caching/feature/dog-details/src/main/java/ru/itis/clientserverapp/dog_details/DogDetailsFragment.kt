package ru.itis.clientserverapp.dog_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.itis.clientserverapp.base_feature.BaseFragment
import ru.itis.clientserverapp.dog_details.constants.DogDetailsConstants
import ru.itis.clientserverapp.dog_details.databinding.FragmentDogDetailsBinding
import ru.itis.clientserverapp.domain.models.DogModel


@AndroidEntryPoint
class DogDetailsFragment : BaseFragment(R.layout.fragment_dog_details) {

    private var viewBinding: FragmentDogDetailsBinding? = null
    private val viewModel: DogDetailsViewModel by viewModels()
    private val dogId by lazy { arguments?.getString(DogDetailsConstants.DOG_ID) ?: "" }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentDogDetailsBinding.inflate(inflater, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        viewModel.loadDogDetails(dogId)
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.dogDetails.collect { dog ->
                    dog?.let { bindDogData(it) }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.error.collect { error ->
                    error?.let {
                        showError(it)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.dataSource.collect { source ->
                    source?.let {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.data_source_message, it.name),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun bindDogData(dog: DogModel) {
        viewBinding?.apply {
            Glide.with(requireContext())
                .load(dog.url)
                .into(ivDog)

            tvBreed.text = dog.breed.name
            tvTemperament.text = DogDetailsConstants.TEMPERAMENT_FORMAT.format(dog.breed.temperament)
            tvWeight.text = DogDetailsConstants.WEIGHT_IMPERIAL_FORMAT.format(dog.breed.weight)
            tvHeight.text = DogDetailsConstants.WEIGHT_METRIC_FORMAT.format(dog.breed.height)
            tvLifeSpan.text = DogDetailsConstants.LIFE_SPAN_FORMAT.format(dog.breed.lifeSpan)
            tvBredFor.text = DogDetailsConstants.BRED_FOR_FORMAT.format(dog.breed.bredFor)
        }
    }

    private fun showError(message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle(DogDetailsConstants.ERROR_TITLE)
            .setMessage(message)
            .setPositiveButton(DogDetailsConstants.POSITIVE_BUTTON_OK, null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}
