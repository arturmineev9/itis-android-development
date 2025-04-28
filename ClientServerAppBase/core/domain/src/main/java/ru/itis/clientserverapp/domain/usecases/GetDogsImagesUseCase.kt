package ru.itis.clientserverapp.domain.usecases

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.itis.clientserverapp.domain.repositories.DogsRepository
import javax.inject.Inject


class GetDogsImagesUseCase @Inject constructor(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val dogsRepository: DogsRepository
) {

    suspend operator fun invoke(): Any {
        return withContext(coroutineDispatcher) {
            dogsRepository.getDogsImages()
        }
    }
}