package ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import newmodel.Player
import ui.component.helpers.DesignSystemUtils.drawBorderBehind
import ui.component.helpers.StyleGuide

@Composable
fun OpponentRow(
    opponentList: List<Player.UI>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.drawBorderBehind(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(StyleGuide.xPadding.value)
    ) {
        Text("Opponents")
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            opponentList.forEach { player ->
                OpponentHand(player = player)
            }
        }
    }
}