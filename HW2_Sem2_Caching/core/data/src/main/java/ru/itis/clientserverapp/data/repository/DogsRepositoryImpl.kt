package ru.itis.clientserverapp.data.repository

import ru.itis.clientserverapp.data.database.dao.DogDao
import ru.itis.clientserverapp.data.mapper.DogsApiResponseMapper
import ru.itis.clientserverapp.network.DogsApi
import ru.itis.clientserverapp.domain.models.DogModel
import ru.itis.clientserverapp.domain.repositories.DogsRepository
import javax.inject.Inject

class DogsRepositoryImpl @Inject constructor(
    private val dogsApi: DogsApi,
    private val mapper: DogsApiResponseMapper,
    private val dogCacheDao: DogDao,
) : DogsRepository {

    private val cacheTimeoutMinutes = 5
    private val maxIntermediateRequests = 3

    override suspend fun getDogsImages(limit: Int): List<DogModel> {
        return dogsApi.searchDogs(limit = limit).let(mapper::mapList)
    }

    override suspend fun getDogDescription(dogId: String): DogModel {

        val currentTime = System.currentTimeMillis()

        val cached = dogCacheDao.getDogById(id = dogId)

        if (cached != null) {
            val minutesPassed = (currentTime - cached.createdAt) / (60 * 1000)
            if (minutesPassed < cacheTimeoutMinutes && cached.requestsSinceLast < maxIntermediateRequests) {
                return cached.let(mapper::toDomainModelFromDbEntity)
            }
        } else {
            dogCacheDao.delete(dogId = dogId)
        }

        val newRequest = dogsApi.getDogDetails(id = dogId)

        dogCacheDao.incrementOtherCounters(dogId)

        if (newRequest.breeds?.isNotEmpty() == true) {
            dogCacheDao.insertDog(newRequest.let(mapper::toDogEntityFromDogResponse))
        }

        return newRequest.let(mapper::map)
    }
}