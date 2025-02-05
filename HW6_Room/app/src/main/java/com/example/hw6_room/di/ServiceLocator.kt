package com.example.hw6_room.di

import android.content.Context
import androidx.room.Room
import com.example.hw6_room.db.AppDatabase
import com.example.hw6_room.db.repository.UserRepository
import com.example.hw6_room.utils.Constants
import kotlinx.coroutines.Dispatchers

object ServiceLocator {

    private var dbInstance: AppDatabase? = null
    private var userRepository: UserRepository? = null

    private fun initDatabase(ctx: Context) {
        dbInstance = Room.databaseBuilder(ctx, AppDatabase::class.java, Constants.DATABASE_NAME)
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
            }
        }
    }

    fun getUserRepository(): UserRepository =
        userRepository ?: throw IllegalStateException("User repository not initialized")

}