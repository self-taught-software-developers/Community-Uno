package ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlayingCard(
    card: newmodel.Card.UI,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(10),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    enabled: Boolean = true,
    onClick: () -> Unit = { }
) {

    val stroke = with(LocalDensity.current) { 8.dp.toPx() }

    Card(
        modifier = modifier.size(
            width = 100.dp,
            height = 140.dp
        ).playingCardDivider(),
        shape = shape,
        onClick = onClick,
        enabled = enabled,
        interactionSource = interactionSource
    ) {
        val face = remember { card.properties.type.value }
        val color = remember { card.getColor() }

        Box(
            modifier = Modifier
                .padding(10.dp)
                .playingCardDivider()
                .clip(shape = shape)
                .background(color = color)
                .padding(5.dp)
                .drawBehind {
                    drawOval(
                        color = Color.Black,
                        style = Stroke(width = stroke)
                    )
                    drawOval(
                        color = Color.White,
                        blendMode = BlendMode.Clear
                    )
                },
            contentAlignment = Alignment.Center
        ) {

            if(card.isFaceShown) {
                when(face) {
                    is newmodel.Card.CardValue.Text -> {
                        Text(
                            text = face.value,
                            fontWeight = FontWeight.ExtraBold,
                            textAlign = TextAlign.Center
                        )
                    }
                    is newmodel.Card.CardValue.Icon -> {
                        Icon(
                            imageVector = face.value,
                            contentDescription = face.value.name
                        )
                    }
                }
            } else {
                Text(
                    text = "COMMUNITY UNO",
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

fun Modifier.playingCardDivider(
    borderWidth: Dp = 4.dp,
    borderColor: Color = Color.Black,
    shape: Shape = RoundedCornerShape(10)
) : Modifier {
    return border(
        width = borderWidth,
        color = borderColor,
        shape = shape
    )
}