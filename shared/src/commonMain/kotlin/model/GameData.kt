package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameData(
    @SerialName("GameDirection")
    val gameDirection: GameDirection = GameDirection.CLOCKWISE,
    @SerialName("CurrentPlayer")
    val currentPlayer: String? = null
) {
    fun isClockwise() : Boolean {
        return gameDirection == GameDirection.CLOCKWISE
    }
}