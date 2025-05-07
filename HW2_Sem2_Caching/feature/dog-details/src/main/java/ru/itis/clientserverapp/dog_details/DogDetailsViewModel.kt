package ru.itis.clientserverapp.dog_details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.itis.clientserverapp.dog_details.constants.DogDetailsConstants
import ru.itis.clientserverapp.domain.models.DogModel
import ru.itis.clientserverapp.domain.usecases.GetDogDetailsUseCase
import javax.inject.Inject

@HiltViewModel
class DogDetailsViewModel @Inject constructor(
    private val getDogDetailsUseCase: GetDogDetailsUseCase
) : ViewModel() {

    private val _dogDetails = MutableStateFlow<DogModel?>(null)
    val dogDetails: StateFlow<DogModel?> = _dogDetails

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadDogDetails(dogId: String) {
        viewModelScope.launch {
            try {
                val dog = getDogDetailsUseCase.invoke(dogId)
                _dogDetails.value = dog
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: DogDetailsConstants.UNKNOWN_ERROR
            }
        }
    }
}
