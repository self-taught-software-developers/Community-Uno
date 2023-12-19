package model

data class CommunityUnoSession(
    val discard: List<Card> = emptyList(),
    val deck: List<Card> = emptyList(),
    val hand: List<Card> = emptyList(),
    val players: List<Player> = emptyList(),
    val playerId: String,
    val gameDirection: GameDirection,
    val currentPlayerId: String? = null
) {

    fun globalColor() : CardColor {
        return discard.last().color
    }
    fun isPlayersTurn(id: String? = null) : Boolean {
        return playerId == (id ?: currentPlayerId)
    }

    fun player() : Player {
        return players.first { it.id == playerId }
    }

    fun currentPlayer(): Player {
        return players.first { it.id == currentPlayerId }
    }
}
