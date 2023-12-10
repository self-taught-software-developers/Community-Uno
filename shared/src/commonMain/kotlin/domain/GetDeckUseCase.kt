package domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import model.Card
import model.CardColor
import model.CardType
import org.koin.core.component.KoinComponent

class GetDeckUseCase : KoinComponent {

    fun invoke(): List<Card> {
        val cards = listOf(
            generateNumericCards(CardColor.RED),
            generateNumericCards(CardColor.GREEN),
            generateNumericCards(CardColor.BLUE),
            generateNumericCards(CardColor.YELLOW),
            generateSpecialCards()
        )

        return (cards + cards).flatten()
    }

}

fun generateNumericCards(
    color: CardColor,
    offset: Int = 0,
    count: Int = 10
): List<Card> {
    return List(count) { number ->
        Card(
            type = CardType.Number,
            name = (number + offset).toString(),
            color = color
        )
    }
}

fun generateSpecialCards(
    normalDuplication: Int = 1,
    specialDuplication: Int = 2
): List<Card> {
    return mutableListOf<Card>().apply {
        CardColor.entries.forEach { color ->
            if (color != CardColor.BLACK) {
                List(normalDuplication) {
                    listOf(
                        Card(color = color, type = CardType.Skip),
                        Card(color = color, type = CardType.Reverse),
                        Card(color = color, type = CardType.Draw2)
                    )
                }.flatten().also(::addAll)
            } else {
                List(specialDuplication) {
                    listOf(
                        Card(color = color, type = CardType.Draw4),
                        Card(color = color, type = CardType.Wild)
                    )
                }.flatten().also(::addAll)
            }
        }
    }
}