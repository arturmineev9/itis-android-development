package ru.itis.clientserverapp.domain.models

data class DogModel(
    val id: String,
    val url: String,
    val breed: BreedModel,
) {
    companion object {
        const val DEFAULT_ID = "default_id"
        const val EMPTY_URL = ""

        fun createDefault(): DogModel {
            return DogModel(
                id = DEFAULT_ID,
                url = EMPTY_URL,
                breed = BreedModel.createDefault()
            )
        }
    }
}
