package ru.itis.clientserverapp.dog_details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.itis.clientserverapp.domain.models.DogModel
import ru.itis.clientserverapp.domain.usecases.GetDogDetailsUseCase
import ru.itis.clientserverapp.utils.enums.DataSource
import javax.inject.Inject

@HiltViewModel
class DogDetailsViewModel @Inject constructor(
    private val getDogDetailsUseCase: GetDogDetailsUseCase
) : ViewModel() {

    private val _dogDetails = MutableStateFlow<DogModel?>(null)
    val dogDetails: StateFlow<DogModel?> = _dogDetails

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _dataSource = MutableStateFlow<DataSource?>(null)
    val dataSource: StateFlow<DataSource?> = _dataSource

    fun loadDogDetails(dogId: String) {
        viewModelScope.launch {
            try {
                val (dog, source) = getDogDetailsUseCase.invoke(dogId)
                _dogDetails.value = dog
                _dataSource.value = source
                _error.value = null

            } catch (e: Exception) {
                _error.value = e.message
                _dataSource.value = null
            }
        }
    }
}
