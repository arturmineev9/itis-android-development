package ru.itis.clientserverapp.data.mapper

import ru.itis.clientserverapp.data.remote.response.DogsImageResponse
import ru.itis.clientserverapp.domain.models.DogImageModel
import javax.inject.Inject

class DogsApiResponseMapper @Inject constructor() {

    fun map(input: DogsImageResponse?) : DogImageModel {
        return DogImageModel(
            id = 1
        )
    }

    fun mapList(responses: List<DogsImageResponse>): List<DogImageModel> {
        return responses.map { map(it) }
    }
}