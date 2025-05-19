package ru.itis.clientserverapp.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.itis.clientserverapp.network.response.DogResponse

interface DogsApi {

    @GET("images/search")
    suspend fun searchDogs(
        @Query("limit") limit: Int = 10,
        @Query("has_breeds") hasBreeds: Int = 1
    ): List<DogResponse>


    @GET("images/{id}")
    suspend fun getDogDetails(
        @Path("id") id: String
    ): DogResponse

}
