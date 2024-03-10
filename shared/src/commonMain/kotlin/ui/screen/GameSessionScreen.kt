package ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import newmodel.Card
import newmodel.Player

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



    }

}