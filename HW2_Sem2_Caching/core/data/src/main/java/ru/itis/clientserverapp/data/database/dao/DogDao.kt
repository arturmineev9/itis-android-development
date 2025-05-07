package ru.itis.clientserverapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.itis.clientserverapp.data.database.entities.DogEntity

@Dao
interface DogDao {
    @Query("SELECT * FROM dogs_api_cache WHERE id = :id")
    suspend fun getDogById(id: String): DogEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDog(dog: DogEntity)

    @Query("DELETE FROM dogs_api_cache WHERE id = :dogId")
    suspend fun delete(dogId: String)

    @Query("UPDATE dogs_api_cache SET requests_since_last = requests_since_last + 1 WHERE id != :currentDogId")
    suspend fun incrementOtherCounters(currentDogId: String)
}