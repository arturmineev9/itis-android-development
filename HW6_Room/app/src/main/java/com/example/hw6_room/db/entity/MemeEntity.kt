package com.example.hw6_room.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "memes")
data class MemeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val url: String,
    val source: String, // "gallery" или "internet"
    val createdAt: Long = System.currentTimeMillis(),
    val tags: String, // Список тегов можно хранить как JSON-строку
    val isFavorite: Boolean = false,
    val userId: Int // ID пользователя, загрузившего мем
)
