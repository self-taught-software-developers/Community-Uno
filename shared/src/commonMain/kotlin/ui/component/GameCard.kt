package ui.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import newmodel.Card
import ui.component.helpers.DesignSystemUtils.cardBorder
import ui.component.helpers.StyleGuide

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlayingCard(
    card: Card.UI,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(10),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    enabled: Boolean = true,
    onClick: () -> Unit = { }
) {

    val px = with(LocalDensity.current) { StyleGuide.StrokeWidth.value.toPx() }

    Card(
        modifier = modifier
            .size(width = 100.dp, height = 140.dp)
            .cardBorder(),
        shape = shape,
        onClick = onClick,
        enabled = enabled,
        interactionSource = interactionSource
    ) {
        val face = remember { card.properties.type.value }
        val color = remember { card.getColor() }

        Box(
            modifier = Modifier
                .cardBorder()
                .clip(shape = shape)
                .background(color = color)
                .drawBehind {
                    drawOval(
                        color = Color.Black,
                        style = Stroke(px)
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
                    is Card.CardValue.Text -> {
                        Text(
                            text = face.value,
                            fontWeight = FontWeight.ExtraBold,
                            textAlign = TextAlign.Center
                        )
                    }
                    is Card.CardValue.Icon -> {
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

@Composable
fun GameCard(
    cardList: List<Card.UI>,
    modifier: Modifier = Modifier,
    angle: Double = 30.0,
    isFanned: Boolean = true,
    enabled: Boolean = true
) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        cardList.forEachIndexed { index, card ->

            val interactionSource = remember { MutableInteractionSource() }
            val isFocused by interactionSource.collectIsHoveredAsState()

            val scale by animateFloatAsState(targetValue = if (isFocused) 1.5f else 1f, label = "scale")
            val raiseY by animateFloatAsState(targetValue = if (isFocused) -50f else 0f, label = "scale")
            val raiseX by animateFloatAsState(targetValue = if (isFocused) 10f else 0f, label = "scale")
            val zIndex by animateFloatAsState(targetValue = if (isFocused) 1f else 0f, label = "zIndex")

            val cardAngle by remember {
                derivedStateOf {
                    ((-angle / 2 + angle / (cardList.count() + 1) * index)
                            ).toFloat()
                }
            }

            PlayingCard(
                modifier = Modifier
                    .graphicsLayer {
                        transformOrigin = if (isFanned) {
                            TransformOrigin(0f, (angle/5).toFloat())
                        } else TransformOrigin.Center

                        translationY = raiseY
                        translationX = raiseX
                        rotationZ =  cardAngle

                    }.scale(scale).zIndex(zIndex),
                interactionSource = interactionSource,
                enabled = enabled,
                card = card
            )
        }
    }

}

