package ru.itis.clientserverapp.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.itis.clientserverapp.data.database.DogsDatabase
import ru.itis.clientserverapp.data.database.dao.DogDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): DogsDatabase {
        return Room.databaseBuilder(
            context,
            DogsDatabase::class.java,
            "my-database.db"
        ).build()
    }

    @Provides
    fun provideDogDao(database: DogsDatabase): DogDao = database.dogDao
}