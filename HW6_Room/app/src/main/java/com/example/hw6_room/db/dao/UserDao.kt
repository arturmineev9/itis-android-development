package com.example.hw6_room.db.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.hw6_room.db.entity.UserEntity

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(userEntity: UserEntity)

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    suspend fun getUserByCredentials(email: String, password: String): UserEntity?

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?
}