package ru.itis.clientserverapp.mainpage.ui

import android.content.Context
import retrofit2.HttpException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.itis.clientserverapp.domain.models.DogModel
import ru.itis.clientserverapp.domain.usecases.GetDogsImagesUseCase
import ru.itis.clientserverapp.mainpage.R
import ru.itis.clientserverapp.navigation.NavMain
import java.io.IOException
import java.net.SocketTimeoutException

@HiltViewModel
class MainPageViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val navMain: NavMain,
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
            try {
                val dogs = getDogsUseCase.invoke(limit)
                _dogs.value = dogs
            } catch (e: HttpException) {
                when (e.code()) {
                    401 -> _error.emit(context.getString(R.string.error_unauthorized))
                    500 -> _error.emit(context.getString(R.string.error_server))
                    else -> _error.emit(context.getString(R.string.error_generic, e.code()))
                }
            } catch (e: SocketTimeoutException) {
                _error.emit(context.getString(R.string.error_network))
            } catch (e: IOException) {
                _error.emit(context.getString(R.string.error_network))
            } catch (e: Exception) {
                _error.emit(context.getString(R.string.error_unknown, e.localizedMessage))
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun onDogClicked(dogId: String) {
        navMain.goToDogDetailsPage(dogId)
    }
}

