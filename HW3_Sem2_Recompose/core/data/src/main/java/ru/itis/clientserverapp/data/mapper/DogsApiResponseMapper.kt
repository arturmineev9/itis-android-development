package ru.itis.clientserverapp.data.mapper

import ru.itis.clientserverapp.data.constants.DogMapperConstants
import ru.itis.clientserverapp.data.database.entities.DogEntity
import ru.itis.clientserverapp.domain.models.BreedModel
import ru.itis.clientserverapp.domain.models.DogModel
import ru.itis.clientserverapp.network.response.DogResponse
import ru.itis.clientserverapp.network.response.Height
import ru.itis.clientserverapp.network.response.Weight
import javax.inject.Inject

class DogsApiResponseMapper @Inject constructor() {

    fun map(input: DogResponse?): DogModel {
        return if (input == null) {
            DogModel.createDefault()
        } else {
            val breed = input.breeds?.firstOrNull()?.let { breedResponse ->
                BreedModel(
                    name = breedResponse.name ?: BreedModel.UNKNOWN,
                    weight = formatWeight(breedResponse.weight),
                    height = formatHeight(breedResponse.height),
                    bredFor = breedResponse.bredFor ?: BreedModel.UNKNOWN,
                    lifeSpan = breedResponse.lifeSpan ?: BreedModel.UNKNOWN,
                    temperament = breedResponse.temperament ?: BreedModel.UNKNOWN
                )
            } ?: BreedModel.createDefault()

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

    fun toDomainModelFromDbEntity(entity: DogEntity): DogModel {
        return DogModel(
            id = entity.id,
            url = entity.url,
            breed = BreedModel(
                name = entity.breedName,
                weight = entity.breedWeight,
                height = entity.breedHeight,
                bredFor = entity.bredFor,
                lifeSpan = entity.lifeSpan,
                temperament = entity.temperament
            )
        )
    }

    fun toDogEntityFromDogResponse(response: DogResponse): DogEntity {
        val breed = response.breeds?.firstOrNull()

        return DogEntity(
            id = response.id,
            url = response.url,
            breedName = breed?.name ?: BreedModel.UNKNOWN,
            breedWeight = breed?.weight?.metric ?: BreedModel.UNKNOWN,
            breedHeight = breed?.height?.metric ?: BreedModel.UNKNOWN,
            bredFor = breed?.bredFor ?: BreedModel.UNKNOWN,
            lifeSpan = breed?.lifeSpan ?: BreedModel.UNKNOWN,
            temperament = breed?.temperament ?: BreedModel.UNKNOWN,
            requestsSinceLast = 0
        )
    }

    private fun formatWeight(weight: Weight?): String {
        return if (weight != null) {
            DogMapperConstants.WEIGHT_FORMAT.format(weight.imperial, weight.metric)
        } else {
            BreedModel.UNKNOWN
        }
    }

    private fun formatHeight(height: Height?): String {
        return if (height != null) {
            DogMapperConstants.HEIGHT_FORMAT.format(height.imperial, height.metric)
        } else {
            BreedModel.UNKNOWN
        }
    }
}
