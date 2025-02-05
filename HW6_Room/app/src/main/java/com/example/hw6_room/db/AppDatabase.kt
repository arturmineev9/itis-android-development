package com.example.hw6_room.db


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.hw6_room.db.dao.UserDao
import com.example.hw6_room.db.entity.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val userDao: UserDao

    companion object {
        const val DB_LOG_KEY = "MemesDB"
    }
}