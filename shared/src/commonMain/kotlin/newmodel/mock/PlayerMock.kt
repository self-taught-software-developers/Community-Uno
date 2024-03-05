package newmodel.mock

import newmodel.Player

fun playerUiMock(
    id: String = ""
) = Player.UI(
    hand = cardUiListMock(
        ownerId = id
    )
)

fun playerUiListMock(
    id: String = ""
) = List(4) { Player.UI(hand = cardUiListMock(ownerId = id)) }