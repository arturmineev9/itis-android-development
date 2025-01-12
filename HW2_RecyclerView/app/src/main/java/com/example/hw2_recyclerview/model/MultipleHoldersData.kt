package com.example.hw2_recyclerview.model

sealed class MultipleHoldersData(
    open val id: String,
    open val headerText: String,
)

class ViewHolderData(
    override val id: String,
    override val headerText: String,
    val description : String,
    val imageUrl: String,
) : MultipleHoldersData(id, headerText)

class ButtonsHolderData(
    override val id: String,
    override val headerText: String
) : MultipleHoldersData(id, headerText)
