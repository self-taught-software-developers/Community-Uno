package ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.dp
import model.Card

@Composable
fun BoxWithConstraintsScope.PlayingCard(
    card: Card,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier.size(
            width = maxWidth/8,
            height = maxWidth/4
        ).drawWithCache {
            onDrawBehind {
                val mod = size

                drawRect(color = card.color.value)

                withTransform({
                    scale(0.7f)
                    rotate(35f)
                }) {
                    drawOval(
                        color = Color.White,
                        size = Size(mod.width, mod.height)
                    )
                }
            }
        },
        backgroundColor = Color.Transparent,
    ) {
        Box(
            modifier = Modifier
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) { Text(card.name) }
    }

}