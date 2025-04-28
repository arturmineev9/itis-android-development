package ru.itis.clientserverapp.data.repository

import ru.itis.clientserverapp.data.mapper.DogsApiResponseMapper
import ru.itis.clientserverapp.data.remote.DogsApi
import ru.itis.clientserverapp.domain.models.DogImageModel
import ru.itis.clientserverapp.domain.repositories.DogsRepository
import javax.inject.Inject

class DogsRepositoryImpl @Inject constructor(
    private val dogsApi: DogsApi,
    private val mapper: DogsApiResponseMapper
) : DogsRepository {


    override suspend fun getDogsImages(): List<DogImageModel> {
        return dogsApi.getDogsImages().let(mapper::mapList)
    }

    override suspend fun getDogDescription(): Any {
        return dogsApi.getDogDescription()
    }
}