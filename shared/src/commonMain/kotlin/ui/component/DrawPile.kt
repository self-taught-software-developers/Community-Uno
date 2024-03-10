package ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import newmodel.Card
import ui.component.helpers.DesignSystemUtils.drawBorderBehind
import ui.component.helpers.StyleGuide

@Composable
fun DrawPile(
    cardList: List<Card.UI>,
    modifier: Modifier = Modifier
) {

    Box(modifier = modifier) {
        Column(
            modifier = Modifier.drawBorderBehind(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(StyleGuide.xPadding.value)
        ) {
            Text("Draw a card")

            GameCard(
                cardList = cardList,
                angle = 0.0,
                isFanned = false
            )
        }
    }
}