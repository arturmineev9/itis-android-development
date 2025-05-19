package ru.itis.clientserverapp.dog_details.compose

import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.itis.clientserverapp.dog_details.viewmodel.DogDetailsViewModel
import ru.itis.clientserverapp.dog_details.R

@Composable
fun DogDetailsScreen(
    viewModel: DogDetailsViewModel,
) {
    val dog by viewModel.dogDetails.collectAsState()
    val error by viewModel.error.collectAsState()
    val dataSource by viewModel.dataSource.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(dataSource) {
        dataSource?.let {
            Toast.makeText(
                context,
                context.getString(R.string.data_source_message, it.name),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    error?.let {
        AlertDialog(
            onDismissRequest = {},
            title = { Text(stringResource(R.string.error_title)) },
            text = { Text(it) },
            confirmButton = {
                TextButton(onClick = {}) {
                    Text(stringResource(R.string.positive_button_ok))
                }
            }
        )
    }

    dog?.let { DogDetailsContent(it) } ?: Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
