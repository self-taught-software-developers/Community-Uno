package ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import newmodel.Card
import ui.component.helpers.StyleGuide

@Composable
fun ActionColumn(
    drawList: List<Card.UI>,
    discardList: List<Card.UI>,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(StyleGuide.xPadding.value)
    ) {
        DrawPile(cardList = drawList)
        DiscardPile(cardList = discardList)
    }

}
