package model

data class CommunityUnoSession(
    val id: String,
    val deck: List<Card> = emptyList(),
    val players: List<Player> = emptyList(),
    val isClockwise: Boolean = true,
    val playerId: String? = null
)
