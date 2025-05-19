package ru.itis.clientserverapp.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.itis.clientserverapp.data.repository.DogsRepositoryImpl
import ru.itis.clientserverapp.domain.repositories.DogsRepository
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface DataBinderModule {

    @Binds
    @Singleton
    fun bindDogRepositoryToImplementation(impl: DogsRepositoryImpl): DogsRepository
}
