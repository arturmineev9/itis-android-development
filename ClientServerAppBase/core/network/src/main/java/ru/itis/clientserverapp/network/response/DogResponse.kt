package ru.itis.clientserverapp.network.response

data class DogResponse(
    val id: String,
    val url: String,
    val breeds: List<BreedResponse>,
    val width: Int,
    val height: Int
)

data class BreedResponse(
    val weight: Weight,
    val height: Height,
    val name: String,
    val bredFor: String?,
    val lifeSpan: String?,
    val temperament: String?
)

data class Weight(
    val imperial: String,
    val metric: String
)

data class Height(
    val imperial: String,
    val metric: String
)