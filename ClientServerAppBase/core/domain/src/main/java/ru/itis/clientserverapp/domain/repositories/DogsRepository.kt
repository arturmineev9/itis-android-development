package ru.itis.clientserverapp.domain.repositories

import ru.itis.clientserverapp.domain.models.DogModel

interface DogsRepository {

    suspend fun getDogsImages(limit: Int) : List<DogModel>

    suspend fun getDogDescription(id: String) : DogModel
}