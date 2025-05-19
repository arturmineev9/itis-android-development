package ru.itis.clientserverapp.graph_screen.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.itis.clientserverapp.graph_screen.constants.GraphScreenConstants

class GraphViewModel : ViewModel() {

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
        val count = _pointCountInput.value.toIntOrNull()
        val inputValues = _valuesInput.value.split(",").mapNotNull { it.trim().toFloatOrNull() }

        when {
            count == null || count <= 0 -> _error.value = GraphScreenConstants.WRONG_POINTS_NUMBER
            inputValues.size != count -> _error.value = GraphScreenConstants.NUMBER_OF_VALUES_MUST_MATCH
            inputValues.any { it < 0 } -> _error.value = GraphScreenConstants.VALUES_MUST_BE_NON_NEGATIVE
            else -> {
                _error.value = null
                _values.value = inputValues
            }
        }
    }
}
