package com.example.hw6_room.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import com.example.hw6_room.db.AppDatabase
import com.example.hw6_room.db.migrations.Migration_1_2
import com.example.hw6_room.db.migrations.Migration_2_3
import com.example.hw6_room.db.repository.MemeRepository
import com.example.hw6_room.db.repository.UserRepository
import com.example.hw6_room.utils.Constants
import kotlinx.coroutines.Dispatchers

object ServiceLocator {

    private var dbInstance: AppDatabase? = null
    private var userRepository: UserRepository? = null
    private var memeRepository: MemeRepository? = null

    private fun initDatabase(ctx: Context) {
        dbInstance = Room.databaseBuilder(ctx, AppDatabase::class.java, Constants.DATABASE_NAME)
            .addMigrations(
                Migration_1_2(),
                Migration_2_3()
            )
            .build()
    }

    fun initDataLayerDependencies(ctx: Context) {
        if (dbInstance == null) {
            initDatabase(ctx)
            dbInstance?.let {
                userRepository = UserRepository(
                    userDao = it.userDao,
                    ioDispatcher = Dispatchers.IO,
                )
                memeRepository = MemeRepository(
                    memeDao = it.memeDao,
                    ioDispatcher = Dispatchers.IO
                )
            }
        }
    }

    fun getUserRepository(): UserRepository =
        userRepository ?: throw IllegalStateException(Constants.USER_REPOSITORY_NOT_INITIALIZED)


    fun getMemeRepository(): MemeRepository =
        memeRepository ?: throw IllegalStateException(Constants.MEME_REPOSITORY_NOT_INITIALIZED)
}