package com.example.hw6_room.db.repository

import com.example.hw6_room.db.dao.MemeDao
import com.example.hw6_room.db.entity.MemeEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class MemeRepository(private val memeDao: MemeDao, private val ioDispatcher: CoroutineDispatcher) {

    suspend fun addMeme(title: String, description: String, url: String, source: String, tags: String, userId: Int) {
        withContext(ioDispatcher) {
            val meme = MemeEntity(
                title = title,
                description = description,
                url = url,
                source = source,
                tags = tags,
                userId = userId
            )
            memeDao.insertMeme(meme)
        }
    }

    suspend fun getUserMemes(userId: Int): List<MemeEntity> {
        return withContext(ioDispatcher) {
            memeDao.getMemesByUser(userId)
        }
    }



    suspend fun getFavoriteMemes(userId: Int): List<MemeEntity> {
        return withContext(ioDispatcher) {
            memeDao.getFavoriteMemesByUser(userId)
        }
    }

    suspend fun deleteMeme(memeId: Int, userId: Int) {
        withContext(ioDispatcher) {
            memeDao.deleteMeme(memeId, userId)
        }
    }

    suspend fun toggleFavorite(memeId: Int, isFavorite: Boolean) {
        withContext(ioDispatcher) {
            memeDao.updateMemeFavoriteStatus(memeId, isFavorite)
        }
    }

    suspend fun getMemeById(memeId: Int): MemeEntity? {
        return withContext(ioDispatcher) {
            memeDao.getMemeById(memeId)
        }
    }

}
