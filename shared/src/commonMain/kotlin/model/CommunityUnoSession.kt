package model

data class CommunityUnoSession(
    val deck: List<Card> = emptyList(),
    val players: List<Player> = emptyList(),
    val id: String,
    val isClockwise: Boolean = true,
    val playerId: String? = null
)
