package ru.itis.clientserverapp.data.repository

import ru.itis.clientserverapp.data.mapper.DogsApiResponseMapper
import ru.itis.clientserverapp.network.DogsApi
import ru.itis.clientserverapp.domain.models.DogModel
import ru.itis.clientserverapp.domain.repositories.DogsRepository
import javax.inject.Inject

class DogsRepositoryImpl @Inject constructor(
    private val dogsApi: DogsApi,
    private val mapper: DogsApiResponseMapper
) : DogsRepository {


    override suspend fun getDogsImages(limit: Int): List<DogModel> {
        return dogsApi.searchDogs(limit = limit).let(mapper::mapList)
    }

    override suspend fun getDogDescription(id: String): DogModel {
        return dogsApi.getDogDetails(id = id).let(mapper::map)
    }
}