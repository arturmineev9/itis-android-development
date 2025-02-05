package com.example.hw6_room.recyclerView

import androidx.core.content.ContextCompat
import com.example.hw6_room.MemeEntity

object MemesRepository {

    fun getMemesList() : List<MemeEntity> = listOf(
        MemeEntity(
            imageUrl = "https://images.squarespace-cdn.com/content/v1/54822a56e4b0b30bd821480c/45ed8ecf-0bb2-4e34-8fcf-624db47c43c8/Golden+Retrievers+dans+pet+care.jpeg",
            description = "first dog"
        ),
        MemeEntity(
            imageUrl = "https://lumiere-a.akamaihd.net/v1/images/open-uri20160811-32147-ybg65r_0a6d1a69.jpeg?region=0,0,640,360",
            description = "second car"
        )
    )

}