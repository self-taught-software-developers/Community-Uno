package newmodel

sealed interface GameSession {

    data class Entity(
        val adminId: String?,
        val currentDealerId: String?,
        val currentPlayerId: String?,
        val playerList: List<Player.Entity>,
        val cardList: List<Card.Entity>,
        val gameBoardDirection: GameBoardDirection,
        val gameState: GameState
    ) : GameSession

}