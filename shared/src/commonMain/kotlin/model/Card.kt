package model

import kotlinx.serialization.Serializable

@Serializable
data class Card(
    private val name: String
)
