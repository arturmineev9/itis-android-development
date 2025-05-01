package ru.itis.clientserverapp.domain.usecases

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.itis.clientserverapp.domain.di.qualifires.IoDispatchers
import ru.itis.clientserverapp.domain.models.DogModel
import ru.itis.clientserverapp.domain.repositories.DogsRepository
import javax.inject.Inject

class GetDogDetailsUseCase @Inject constructor(
    @IoDispatchers private val coroutineDispatcher: CoroutineDispatcher,
    private val dogsRepository: DogsRepository
) {

    suspend operator fun invoke(id: String): DogModel {
        return withContext(coroutineDispatcher) {
            dogsRepository.getDogDescription(id)
        }
    }
}