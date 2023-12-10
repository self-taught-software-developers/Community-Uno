package model

import kotlinx.serialization.Serializable

@Serializable
enum class CardType {
    Number,
    Skip,
    Reverse,
    Draw2,
    Draw4,
    Wild
}