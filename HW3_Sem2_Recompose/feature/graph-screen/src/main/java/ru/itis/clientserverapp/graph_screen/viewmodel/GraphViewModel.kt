package ru.itis.clientserverapp.graph_screen.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.itis.clientserverapp.graph_screen.R
import javax.inject.Inject

@HiltViewModel
class GraphViewModel @Inject constructor(
    @ApplicationContext private val context: Context
): ViewModel() {

    private val _pointCountInput = MutableStateFlow<String>("")
    val pointCountInput: StateFlow<String> = _pointCountInput

    private val _valuesInput = MutableStateFlow<String>("")
    val valuesInput: StateFlow<String> = _valuesInput

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _values = MutableStateFlow<List<Float>>(emptyList())
    val values: StateFlow<List<Float>> = _values

    fun updatePointCountInput(input: String) {
        _pointCountInput.value = input
    }

    fun updateValuesInput(input: String) {
        _valuesInput.value = input
    }

    fun buildGraph() {
        val count = pointCountInput.value.toIntOrNull()
        val inputValues = valuesInput.value.split(",").mapNotNull { it.trim().toFloatOrNull() }

        when {
            count == null || count <= 0 ->
                _error.value = context.getString(R.string.wrong_points_number)
            inputValues.size != count ->
                _error.value = context.getString(R.string.number_of_values_must_match)
            inputValues.any { it < 0 } ->
                _error.value = context.getString(R.string.values_must_be_non_negative)
            else -> {
                _error.value = null
                _values.value = inputValues
            }
        }
    }
}
