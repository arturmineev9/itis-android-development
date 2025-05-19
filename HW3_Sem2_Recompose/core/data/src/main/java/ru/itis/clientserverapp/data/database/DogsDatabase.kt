package ru.itis.clientserverapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.itis.clientserverapp.data.database.dao.DogCacheDao
import ru.itis.clientserverapp.data.database.entities.DogEntity

@Database(
    entities = [DogEntity::class],
    version = 1
)

abstract class DogsDatabase : RoomDatabase() {
    abstract val dogDao: DogCacheDao
}
