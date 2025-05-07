package ru.itis.clientserverapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.itis.clientserverapp.data.database.converters.Converters
import ru.itis.clientserverapp.data.database.dao.DogDao
import ru.itis.clientserverapp.data.database.entities.DogEntity


@Database(
    entities = [DogEntity::class],
    version = 1
)

@TypeConverters(Converters::class)
abstract class DogsDatabase : RoomDatabase() {
    abstract val dogDao: DogDao
}