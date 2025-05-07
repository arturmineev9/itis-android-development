package ru.itis.clientserverapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dogs_api_cache")
data class DogEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo("url")
    val url: String,
    @ColumnInfo("breed_name")
    val breedName: String,
    @ColumnInfo("breed_weight")
    val breedWeight: String,
    @ColumnInfo("breed_height")
    val breedHeight: String,
    @ColumnInfo("bred_for")
    val bredFor: String,
    @ColumnInfo("life_span")
    val lifeSpan: String,
    @ColumnInfo("temperament")
    val temperament: String,
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo("requests_since_last")
    val requestsSinceLast: Int
)