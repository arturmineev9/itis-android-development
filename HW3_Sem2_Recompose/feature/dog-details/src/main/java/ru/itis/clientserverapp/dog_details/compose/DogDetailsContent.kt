package ru.itis.clientserverapp.dog_details.compose

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ru.itis.clientserverapp.domain.models.DogModel
import ru.itis.clientserverapp.dog_details.R

@Composable
fun DogDetailsContent(dog: DogModel) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        AsyncImage(
            model = dog.url,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = dog.breed.name,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(stringResource(R.string.temperament_format, dog.breed.temperament))
        Spacer(modifier = Modifier.height(8.dp))

        Text(stringResource(R.string.weight_imperial_format, dog.breed.weight))
        Text(stringResource(R.string.weight_metric_format, dog.breed.height))
        Spacer(modifier = Modifier.height(8.dp))

        Text(stringResource(R.string.life_span_format, dog.breed.lifeSpan))
        Spacer(modifier = Modifier.height(8.dp))

        Text(stringResource(R.string.bred_for_format, dog.breed.bredFor))
        Spacer(modifier = Modifier.height(8.dp))
    }
}
