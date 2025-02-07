package com.example.hw6_room.db.dao

import androidx.room.*
import com.example.hw6_room.db.entity.MemeEntity

@Dao
interface MemeDao {

    @Insert
    suspend fun insertMeme(meme: MemeEntity)

    @Query("SELECT * FROM memes WHERE userId = :userId")
    suspend fun getMemesByUser(userId: Int): List<MemeEntity>

    @Query("SELECT * FROM memes WHERE userId = :userId AND isFavorite = 1")
    suspend fun getFavoriteMemesByUser(userId: Int): List<MemeEntity>

    @Query("DELETE FROM memes WHERE id = :memeId AND userId = :userId")
    suspend fun deleteMeme(memeId: Int, userId: Int)

    @Query("UPDATE memes SET isFavorite = :isFavorite WHERE id = :memeId")
    suspend fun updateMemeFavoriteStatus(memeId: Int, isFavorite: Boolean)

}
