package newmodel.mock

import newmodel.Player

fun playerUiMock(
    id: String = ""
) = Player.UI(
    name = "Me ${id.take(4)}",
    hand = cardUiListMock(
        ownerId = id
    ).map { it.copy(isFaceShown = true) }
)


fun opponentUiMock(
    id: String = ""
) = Player.UI(
    name = "Player ${id.take(4)}",
    hand = cardUiListMock(
        ownerId = id
    )
)

fun playerUiListMock(
    id: String = ""
) = List(3) { index ->
    opponentUiMock(id = "$index$id")
}