package ru.itis.clientserverapp.domain.repositories

import ru.itis.clientserverapp.domain.models.DogImageModel

interface DogsRepository {

    suspend fun getDogsImages() : List<DogImageModel>

    suspend fun getDogDescription() : Any
}