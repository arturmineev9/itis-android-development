package ru.itis.clientserverapp.dog_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.itis.clientserverapp.domain.models.DogModel
import ru.itis.clientserverapp.domain.usecases.GetDogDetailsUseCase
import javax.inject.Inject

@HiltViewModel
class DogDetailsViewModel @Inject constructor(
    private val getDogDetailsUseCase: GetDogDetailsUseCase
) : ViewModel() {

    private val _dogDetails = MutableStateFlow<DogModel?>(null)
    val dogDetails: StateFlow<DogModel?> = _dogDetails

    fun loadDogDetails(dogId: String) {
        viewModelScope.launch {
            runCatching {
                getDogDetailsUseCase.invoke(dogId)
            }
                .onSuccess { _dogDetails.value = it }
                .onFailure { /* Обработка ошибок */ }
        }
    }
}