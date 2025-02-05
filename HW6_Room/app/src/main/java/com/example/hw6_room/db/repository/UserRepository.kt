package com.example.hw6_room.db.repository

import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.hw6_room.db.dao.UserDao
import com.example.hw6_room.db.entity.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class UserRepository(private val userDao: UserDao, private val ioDispatcher: CoroutineDispatcher) {

    suspend fun registerUser(username: String, email: String, password: String) {
        return withContext(ioDispatcher) {
            val hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray())
            val user = User(username = username, email = email, password = hashedPassword)
            userDao.insertUser(user)
        }
    }

    suspend fun loginUser(email: String, password: String): User? {
        return withContext(ioDispatcher) {
            val user = userDao.getUserByEmail(email)
            if (user != null && BCrypt.verifyer()
                    .verify(password.toCharArray(), user.password).verified
            ) {
                user
            } else {
                null
            }
        }
    }

    suspend fun getUserByEmail(email: String): User? {
        return withContext(ioDispatcher) {
            userDao.getUserByEmail(email)
        }
    }
}