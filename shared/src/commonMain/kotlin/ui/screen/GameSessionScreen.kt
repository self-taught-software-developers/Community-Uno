package ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import newmodel.Card
import newmodel.Player
import ui.component.ActionColumn
import ui.component.OpponentRow
import ui.component.PlayerColumn
import ui.component.PlayerHand

@Composable
fun GameSessionScreen(
    player: Player.UI?,
    opponentList: List<Player.UI>,
    drawList: List<Card.UI>,
    discardList: List<Card.UI>,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        ActionColumn(
            modifier = Modifier,
            drawList = drawList,
            discardList = discardList
        )

        PlayerColumn(
            modifier = Modifier
        ) {
            OpponentRow(
                modifier = Modifier,
                opponentList = opponentList
            )
            if (player != null) {
                PlayerHand(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    player = player
                )
            }
        }

    }

}