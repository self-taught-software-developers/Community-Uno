package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameData(
    @SerialName("GameDirection")
    val isClockwise: Boolean = true,
    @SerialName("CurrentPlayer")
    val currentPlayer: String? = null
)