package com.example.hw4_themes_notifications

import android.app.NotificationManager

enum class ImportanceTypes(val importance: Int) {
    MAX(NotificationManager.IMPORTANCE_MAX),
    HIGH(NotificationManager.IMPORTANCE_HIGH),
    DEFAULT(NotificationManager.IMPORTANCE_DEFAULT),
    LOW(NotificationManager.IMPORTANCE_LOW);

    companion object {
        // Метод для получения enum по строковому значению
        fun fromString(priority: String): ImportanceTypes {
            return when (priority.uppercase()) {
                "MAX" -> MAX
                "HIGH" -> HIGH
                "DEFAULT" -> DEFAULT
                "LOW" -> LOW
                else -> DEFAULT // Значение по умолчанию
            }
        }
    }
}
