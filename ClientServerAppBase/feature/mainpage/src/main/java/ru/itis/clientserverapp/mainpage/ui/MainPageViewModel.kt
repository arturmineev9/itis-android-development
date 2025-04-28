package ru.itis.clientserverapp.mainpage.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import ru.itis.clientserverapp.domain.usecases.GetDogsImagesUseCase

@HiltViewModel
class MainPageViewModel @Inject constructor(
    private val getDogsImagesUseCase: GetDogsImagesUseCase
): ViewModel() {

    //private val _dataFlow = MutableStateFlow()
}