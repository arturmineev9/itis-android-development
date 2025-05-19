package ru.itis.clientserverapp.dog_details.compose

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ru.itis.clientserverapp.domain.models.DogModel
import ru.itis.clientserverapp.dog_details.constants.DogDetailsConstants

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

        Text(DogDetailsConstants.TEMPERAMENT_FORMAT.format(dog.breed.temperament))
        Spacer(modifier = Modifier.height(8.dp))

        Text(DogDetailsConstants.WEIGHT_IMPERIAL_FORMAT.format(dog.breed.weight))
        Text(DogDetailsConstants.WEIGHT_METRIC_FORMAT.format(dog.breed.height))
        Spacer(modifier = Modifier.height(8.dp))

        Text(DogDetailsConstants.LIFE_SPAN_FORMAT.format(dog.breed.lifeSpan))
        Spacer(modifier = Modifier.height(8.dp))

        Text(DogDetailsConstants.BRED_FOR_FORMAT.format(dog.breed.bredFor))
        Spacer(modifier = Modifier.height(8.dp))

    }
}
