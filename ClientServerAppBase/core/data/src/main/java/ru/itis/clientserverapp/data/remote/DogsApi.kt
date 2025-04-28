package ru.itis.clientserverapp.data.remote

import retrofit2.http.GET
import ru.itis.clientserverapp.data.remote.response.DogsImageResponse
import ru.itis.clientserverapp.domain.models.DogImageModel

interface DogsApi {

    @GET("/")
    suspend fun getDogsImages(): List<DogsImageResponse>

    @GET("/")
    suspend fun getDogDescription(): Any
}