package ru.itis.clientserverapp.data.mapper

import ru.itis.clientserverapp.domain.models.Breed
import ru.itis.clientserverapp.network.response.DogResponse
import ru.itis.clientserverapp.domain.models.DogModel
import ru.itis.clientserverapp.network.response.Height
import ru.itis.clientserverapp.network.response.Weight
import javax.inject.Inject

class DogsApiResponseMapper @Inject constructor() {

    fun map(input: DogResponse?): DogModel {
        return if (input == null) {
            createDefaultDogModel()
        } else {
            val breed = input.breeds.firstOrNull()?.let { breedResponse ->
                Breed(
                    weight = formatWeight(breedResponse.weight),
                    height = formatHeight(breedResponse.height),
                    bredFor = breedResponse.bredFor ?: "Unknown",
                    lifeSpan = breedResponse.lifeSpan ?: "Unknown",
                    temperament = breedResponse.temperament ?: "Unknown"
                )
            } ?: createDefaultBreed()

            DogModel(
                id = input.id,
                url = input.url,
                breed = breed
            )
        }
    }

    fun mapList(responses: List<DogResponse>): List<DogModel> {
        return responses.map { map(it) }
    }

    private fun formatWeight(weight: Weight): String {
        return "${weight.imperial} lbs (${weight.metric} kg)"
    }

    private fun formatHeight(height: Height): String {
        return "${height.imperial} in (${height.metric} cm)"
    }

    private fun createDefaultBreed(): Breed {
        return Breed(
            weight = "Unknown",
            height = "Unknown",
            bredFor = "Unknown",
            lifeSpan = "Unknown",
            temperament = "Unknown"
        )
    }

    private fun createDefaultDogModel(): DogModel {
        return DogModel(
            id = "0",
            url = "",
            breed = createDefaultBreed()
        )
    }
}