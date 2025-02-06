package com.example.hw6_room.db


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.hw6_room.db.dao.MemeDao
import com.example.hw6_room.db.dao.UserDao
import com.example.hw6_room.db.entity.User
import com.example.hw6_room.db.entity.MemeEntity

@Database(entities = [User::class, MemeEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val memeDao: MemeDao

    companion object {
        const val DB_LOG_KEY = "MemesDB"
    }
}
