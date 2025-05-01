package ru.itis.clientserverapp.network.response

import com.google.gson.annotations.SerializedName

data class DogResponse(
    @SerializedName("id") val id: String,
    @SerializedName("url") val url: String,
    @SerializedName("breeds") val breeds: List<BreedResponse>?,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int
)

data class BreedResponse(
    @SerializedName("name") val name: String?,
    @SerializedName("weight") val weight: Weight?,
    @SerializedName("height") val height: Height?,
    @SerializedName("bred_for") val bredFor: String?,
    @SerializedName("life_span") val lifeSpan: String?,
    @SerializedName("temperament") val temperament: String?
)

data class Weight(
    val imperial: String,
    val metric: String
)

data class Height(
    val imperial: String,
    val metric: String
)