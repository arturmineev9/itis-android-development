package ru.itis.clientserverapp.domain.models

data class BreedModel(
    val name: String,
    val weight: String,
    val height: String,
    val bredFor: String,
    val lifeSpan: String,
    val temperament: String
) {
    companion object {
        const val UNKNOWN = "Unknown"

        fun createDefault(): BreedModel {
            return BreedModel(
                name = UNKNOWN,
                weight = UNKNOWN,
                height = UNKNOWN,
                bredFor = UNKNOWN,
                lifeSpan = UNKNOWN,
                temperament = UNKNOWN
            )
        }
    }
}
