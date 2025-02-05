package com.example.hw6_room.db.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.hw6_room.db.entity.User

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    suspend fun getUserByCredentials(email: String, password: String): User?

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?
}