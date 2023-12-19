package domain

import model.Card
import model.CardType
import model.GameDirection
import model.Player

class GetPlayCardUseCase(
    private val default: PlayDefaultCardUseCase,
    private val skip: PlaySkipPlayerUseCase,
    private val reverse: PlayReverseGameDirectionUseCase,
    private val multiple: PlayDrawCardMultipleUseCase,
) {

    suspend operator fun invoke(
        card: Card,
        deck: List<Card>,
        players: List<Player>,
        currentPlayer: Player,
        direction: GameDirection
    ) {

        when(card.type) {
            CardType.Skip -> skip.invoke(card, players, currentPlayer, direction)
            CardType.Reverse -> reverse.invoke(card, players, currentPlayer, direction)
            CardType.Draw2 -> multiple.invoke(2, card, deck, players, currentPlayer, direction)
            CardType.Draw4 -> multiple.invoke(4, card, deck, players, currentPlayer, direction)
            CardType.Wild, CardType.Number -> default.invoke(card, players, currentPlayer, direction)
        }

    }
}