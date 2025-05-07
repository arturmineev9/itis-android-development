package ru.itis.clientserverapp.mainpage.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.itis.clientserverapp.mainpage.constants.MainPageConstants
import ru.itis.clientserverapp.mainpage.databinding.FragmentMainPageBinding
import ru.itis.clientserverapp.mainpage.recyclerview.DogsAdapter


@AndroidEntryPoint
class MainPageFragment : Fragment() {

    private var viewBinding: FragmentMainPageBinding? = null

    private val viewModel: MainPageViewModel by viewModels()
    private lateinit var adapter: DogsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentMainPageBinding.inflate(inflater, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()
        setupListeners()
    }

    private fun setupRecyclerView() {
        adapter = DogsAdapter { dog ->
            viewModel.onDogClicked(dog.id)
        }
        viewBinding?.rvDogs?.layoutManager = GridLayoutManager(requireContext(), 2)
        viewBinding?.rvDogs?.adapter = adapter
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.dogs.collect { adapter.submitList(it) }
                viewModel.isLoading.collect { updateLoadingState(it) }
                viewModel.error.collect { showError(it) }
            }
        }
    }

    private fun updateLoadingState(isLoading: Boolean) {
        viewBinding?.apply {
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            btnLoad.isEnabled = !isLoading
            rvDogs.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
        }

    }

    private fun showError(message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle(MainPageConstants.ERROR_TITLE)
            .setMessage(message)
            .setPositiveButton(MainPageConstants.POSITIVE_BUTTON_OK, null)
            .show()
    }

    private fun setupListeners() {
        viewBinding?.btnLoad?.setOnClickListener {
            val input = viewBinding?.etLimit?.text.toString()
            val limit = input.toIntOrNull()

            if (limit == null || limit <= 0) {
                showError(MainPageConstants.INVALID_INPUT)
            } else {
                viewModel.loadDogs(limit)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}
