package model

import kotlinx.serialization.Serializable

@Serializable
data class Card(
    val type: CardType,
    val name: String = type.name,
    val color: CardColor
)
