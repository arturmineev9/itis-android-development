package ru.itis.clientserverapp.dog_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.collection.intFloatMapOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.itis.clientserverapp.base.BaseFragment
import ru.itis.clientserverapp.dog_details.databinding.FragmentDogDetailsBinding
import ru.itis.clientserverapp.domain.models.DogModel


@AndroidEntryPoint
class DogDetailsFragment : BaseFragment(R.layout.fragment_dog_details) {

    private var viewBinding: FragmentDogDetailsBinding? = null
    private val viewModel: DogDetailsViewModel by viewModels()
    private val dogId by lazy { arguments?.getString("DOG_ID") ?: "" }

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
    }

    private fun bindDogData(dog: DogModel) {
        viewBinding?.apply {

            Glide.with(requireContext())
                .load(dog.url)
                .into(ivDog)
            
            tvBreed.text = dog.breed.name
            tvTemperament.text = getString(R.string.temperament_format, dog.breed.temperament)
            tvWeightImperial.text = getString(R.string.weight_imperial_format, dog.breed.weight)
            tvWeightMetric.text = getString(R.string.weight_metric_format, dog.breed.weight)
            tvLifeSpan.text = getString(R.string.life_span_format, dog.breed.lifeSpan)
            tvBredFor.text = getString(R.string.bred_for_format, dog.breed.bredFor)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}