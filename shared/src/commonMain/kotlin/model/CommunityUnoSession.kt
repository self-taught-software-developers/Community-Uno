package model

import androidx.compose.ui.graphics.Color

data class CommunityUnoSession(
    val discard: List<Card> = emptyList(),
    val deck: List<Card> = emptyList(),
    val hand: List<Card> = emptyList(),
    val players: List<Player> = emptyList(),
    val playerId: String,
    val isClockwise: Boolean = true,
    val currentPlayerId: String? = null
) {
    fun globalColor() : Color {
        return discard.lastOrNull()?.color?.value ?: Color.Transparent
    }

    fun isPlayersTurn(id: String? = null) : Boolean {
        return playerId == (id ?: currentPlayerId)
    }
}
