package ru.itis.clientserverapp.domain.models

data class DogModel(
    val id: String,
    val url: String,
    val breed: Breed,
)

data class Breed(
    val name: String,
    val weight: String,
    val height: String,
    val bredFor: String,
    val lifeSpan: String,
    val temperament: String
)
