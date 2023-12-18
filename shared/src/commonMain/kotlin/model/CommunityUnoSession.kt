package model

import androidx.compose.ui.graphics.Color

data class CommunityUnoSession(
    val discard: List<Card> = emptyList(),
    val deck: List<Card> = emptyList(),
    val hand: List<Card> = emptyList(),
    val players: List<Player> = emptyList(),
    val id: String,
    val isClockwise: Boolean = true,
    val playerId: String? = null
) {
    fun globalColor() : Color {
        return discard.lastOrNull()?.color?.value ?: Color.Transparent
    }
}
