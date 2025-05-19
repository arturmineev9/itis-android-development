package ru.itis.clientserverapp.graph_screen.compose

import android.graphics.Color
import android.view.LayoutInflater
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.EntryXComparator
import java.util.*

@Composable
fun GraphScreen() {
    var pointCountInput by remember { mutableStateOf("") }
    var valuesInput by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    var values by remember { mutableStateOf<List<Float>>(emptyList()) }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        OutlinedTextField(
            value = pointCountInput,
            onValueChange = { pointCountInput = it },
            label = { Text("Количество точек") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = valuesInput,
            onValueChange = { valuesInput = it },
            label = { Text("Значения через запятую") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        error?.let {
            Text(it, color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(onClick = {
            val count = pointCountInput.toIntOrNull()
            val inputValues = valuesInput.split(",").mapNotNull { it.trim().toFloatOrNull() }

            when {
                count == null || count <= 0 -> error = "Некорректное количество точек"
                inputValues.size != count -> error = "Количество значений должно совпадать"
                inputValues.any { it < 0 } -> error = "Все значения должны быть неотрицательными"
                else -> {
                    error = null
                    values = inputValues
                }
            }
        }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text("Построить график")
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (values.isNotEmpty()) {
            LineChartView(values)
        }
    }
}
