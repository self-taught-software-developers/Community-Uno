package newmodel.mock

import newmodel.Card
import kotlin.random.Random

fun cardUiMock(
    ownerId: String = ""
) = Card.Entity(
    id = Random.nextDouble().toString(),
    ownerId = ownerId,
    properties = Card.Properties(
        type = Card.CardType.entries.random(),
        color = Card.CardColor.entries.random()
    ),
    isPlayed = false,
)


fun cardUiListMock(
    ownerId: String = ""
) = List(10) { cardUiMock(ownerId) }
