package domain

import model.Card
import model.CardColor
import model.CardType

//TODO FIX DECK ID GENERATION.
fun getDeck(): List<Card> = listOf(
    generateNumericCards(CardColor.RED),
    generateNumericCards(CardColor.GREEN),
    generateNumericCards(CardColor.BLUE),
    generateNumericCards(CardColor.YELLOW),
    generateSpecialCards()
).flatten().mapIndexed { index, card -> card.copy(key = index.toString()) }

fun generateNumericCards(
    color: CardColor,
    offset: Int = 0,
    copies: Int = 2,
    count: Int = 10
): List<Card> = mutableListOf<Card>().apply {
    repeat(copies) {
        List(count) { number ->
            Card(
                type = CardType.Number,
                name = (number + offset).toString(),
                color = color
            )
        }.also(::addAll)
    }
}

fun generateSpecialCards(
    normalDuplication: Int = 2,
    specialDuplication: Int = 4
): List<Card> = mutableListOf<Card>().apply {
    CardColor.entries.forEach { color ->
        if (color != CardColor.BLACK) {
            repeat(normalDuplication) {
                listOf(
                    Card(color = color, type = CardType.Skip),
                    Card(color = color, type = CardType.Reverse),
                    Card(color = color, type = CardType.Draw2)
                ).also(::addAll)
            }
        } else {
            repeat(specialDuplication) {
                listOf(
                    Card(color = color, type = CardType.Draw4),
                    Card(color = color, type = CardType.Wild)
                ).also(::addAll)
            }
        }
    }
}