package ru.itis.clientserverapp.mainpage.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.itis.clientserverapp.domain.models.DogModel
import ru.itis.clientserverapp.domain.usecases.GetDogsImagesUseCase

@HiltViewModel
class MainPageViewModel @Inject constructor(
    private val getDogsUseCase: GetDogsImagesUseCase
) : ViewModel() {
    private val _dogs = MutableStateFlow<List<DogModel>>(emptyList())
    val dogs: StateFlow<List<DogModel>> = _dogs

    private val _error = MutableSharedFlow<String>()
    val error: SharedFlow<String> = _error

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadDogs(limit: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            runCatching {
                getDogsUseCase.invoke(limit = limit)
            }
                .onSuccess { _dogs.value = it }
                .onFailure { _error.emit("Ошибка загрузки: ${it.message}") }
            _isLoading.value = false
        }
    }
}