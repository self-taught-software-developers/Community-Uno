package model

import kotlinx.serialization.Serializable

@Serializable
data class GameSession(
    val adminId: String,
    val currentDealerId: String?,
    val currentPlayerId: String?,
    val cardList: List<Card>,
    val playerList: List<Player>,
    val gameDirection: GameDirection,
    val isGameActive: Boolean
)
