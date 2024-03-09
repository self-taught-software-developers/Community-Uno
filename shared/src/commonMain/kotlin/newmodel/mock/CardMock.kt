package newmodel.mock

import newmodel.Card
import kotlin.random.Random

fun cardUiMock(
    ownerId: String = "",
    type: Card.CardType = Card.CardType.entries.random()
) = Card.UI(
    id = Random.nextDouble().toString(),
    ownerId = ownerId,
    properties = Card.Properties(
        type = type,
        color = when(type) {
            Card.CardType.WILD, Card.CardType.DRAW_4 -> Card.CardColor.BLACK
            else -> Card.CardColor.entries
                .dropLast(1)
                .random()
        }
    ),
    isFaceShown = false
)



fun cardUiListMock(
    ownerId: String = ""
) = List(10) { cardUiMock(ownerId) }
