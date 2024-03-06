package ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.gitlive.firebase.firestore.externals.collection
import model.Card

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlayingCard(
    card: newmodel.Card.Entity?,
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
        val cardValue = remember { (card?.properties?.type?.value) }
        val color = remember { card?.properties?.color?.value ?: Color.Black }

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
            when(cardValue) {
                is newmodel.Card.CardValue.Text -> {
                    Text(
                        text = cardValue.value,
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center
                    )
                }
                is newmodel.Card.CardValue.Icon -> {
                    Icon(
                        imageVector = cardValue.value,
                        contentDescription = cardValue.value.name
                    )
                }

                null -> {
                    Text(
                        text = "COMMUNITY UNO",
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center
                    )
                }
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