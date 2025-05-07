package ru.itis.clientserverapp.data.mapper

import android.util.Log
import ru.itis.clientserverapp.data.constants.DogMapperConstants
import ru.itis.clientserverapp.data.database.entities.DogEntity
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
            val breed = input.breeds?.firstOrNull()?.let { breedResponse ->
                Breed(
                    name = breedResponse.name ?: DogMapperConstants.UNKNOWN,
                    weight = formatWeight(breedResponse.weight),
                    height = formatHeight(breedResponse.height),
                    bredFor = breedResponse.bredFor ?: DogMapperConstants.UNKNOWN,
                    lifeSpan = breedResponse.lifeSpan ?: DogMapperConstants.UNKNOWN,
                    temperament = breedResponse.temperament ?: DogMapperConstants.UNKNOWN
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

    fun toDomainModelFromDbEntity(entity: DogEntity): DogModel {
        return DogModel(
            id = entity.id,
            url = entity.url,
            breed = Breed(
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
            breedName = breed?.name ?: DogMapperConstants.UNKNOWN,
            breedWeight = breed?.weight?.metric ?: DogMapperConstants.UNKNOWN,
            breedHeight = breed?.height?.metric ?: DogMapperConstants.UNKNOWN,
            bredFor = breed?.bredFor ?: DogMapperConstants.UNKNOWN,
            lifeSpan = breed?.lifeSpan ?: DogMapperConstants.UNKNOWN,
            temperament = breed?.temperament ?: DogMapperConstants.UNKNOWN,
            requestsSinceLast = 0
        )
    }

    private fun formatWeight(weight: Weight?): String {
        return if (weight != null) {
            DogMapperConstants.WEIGHT_FORMAT.format(weight.imperial, weight.metric)
        } else {
            DogMapperConstants.UNKNOWN
        }
    }

    private fun formatHeight(height: Height?): String {
        return if (height != null) {
            DogMapperConstants.HEIGHT_FORMAT.format(height.imperial, height.metric)
        } else {
            DogMapperConstants.UNKNOWN
        }
    }

    private fun createDefaultBreed(): Breed {
        return Breed(
            name = DogMapperConstants.UNKNOWN,
            weight = DogMapperConstants.UNKNOWN,
            height = DogMapperConstants.UNKNOWN,
            bredFor = DogMapperConstants.UNKNOWN,
            lifeSpan = DogMapperConstants.UNKNOWN,
            temperament = DogMapperConstants.UNKNOWN
        )
    }

    private fun createDefaultDogModel(): DogModel {
        return DogModel(
            id = DogMapperConstants.DEFAULT_ID,
            url = DogMapperConstants.EMPTY_URL,
            breed = createDefaultBreed()
        )
    }
}
