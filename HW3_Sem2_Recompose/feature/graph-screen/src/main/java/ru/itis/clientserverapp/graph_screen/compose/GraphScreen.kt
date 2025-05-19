package ru.itis.clientserverapp.graph_screen.compose

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.itis.clientserverapp.graph_screen.constants.GraphScreenConstants
import ru.itis.clientserverapp.graph_screen.viewmodel.GraphViewModel

@Composable
fun GraphScreen(
    viewModel: GraphViewModel
) {

    val pointCountInput by viewModel.pointCountInput.collectAsState()
    val valuesInput by viewModel.valuesInput.collectAsState()
    val error by viewModel.error.collectAsState()
    val values by viewModel.values.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = pointCountInput,
            onValueChange = viewModel::updatePointCountInput,
            label = { Text(GraphScreenConstants.POINTS_NUMBER) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = valuesInput,
            onValueChange = viewModel::updateValuesInput,
            label = { Text(GraphScreenConstants.VALUES) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        error?.let {
            Text(it, color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(
            onClick = viewModel::buildGraph,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(GraphScreenConstants.BUILD_GRAPH)
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (values.isNotEmpty()) {
            LineChartView(values)
        }
    }
}
