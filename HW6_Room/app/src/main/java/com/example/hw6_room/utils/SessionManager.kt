package com.example.hw6_room.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(Constants.USER_SESSION, Context.MODE_PRIVATE)

    // Сохранение данных пользователя после входа
    fun saveUserSession(userId: Int, email: String) {
        val editor = sharedPreferences.edit()
        editor.putInt(Constants.KEY_USER_ID, userId)
        editor.putString(Constants.KEY_USER_EMAIL, email)
        editor.putBoolean(Constants.KEY_IS_LOGGED_IN, true)
        editor.apply()
    }

    // Проверка, авторизован ли пользователь
    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(Constants.KEY_IS_LOGGED_IN, false)
    }

    // Получение данных пользователя
    fun getUserId(): Int {
        return sharedPreferences.getInt(Constants.KEY_USER_ID, -1)
    }

    fun getUserEmail(): String? {
        return sharedPreferences.getString(Constants.KEY_USER_EMAIL, null)
    }

    // Очистка данных при выходе
    fun logoutUser() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}