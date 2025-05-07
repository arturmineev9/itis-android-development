package ru.itis.clientserverapp.domain.repositories

import ru.itis.clientserverapp.domain.models.DogModel
import ru.itis.clientserverapp.utils.enums.DataSource

interface DogsRepository {

    suspend fun getDogsImages(limit: Int) : List<DogModel>

    suspend fun getDogDescription(id: String) : Pair<DogModel, DataSource>
}
