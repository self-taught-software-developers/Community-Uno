package ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.zIndex
import newmodel.Card
import newmodel.Player
import ui.component.PlayingCard
import ui.component.StyleGuide

@Composable
fun GameTableLayout(
    activePlayer: Player.UI?,
    otherPlayerList: List<Player.UI>,
    drawPileList: List<Card.UI>,
    discardPileList: List<Card.UI>,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Column {
            CardLayout(
                cardList = drawPileList,
                isFanned = false
            )

            CardLayout(
                cardList = discardPileList,
                isFanned = false
            )
        }
        Column(
            modifier = Modifier
                .padding(StyleGuide.xPadding.value),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            otherPlayerList.forEachIndexed { index, player ->
                    PlayerHandLayout(
                        player = player
                    )
                }

            if (activePlayer != null) {

                PlayerHandLayout(
                    modifier = Modifier.weight(1f),
                    player = activePlayer
                )

            }
        }
    }

}

@Composable
fun PlayerHandLayout(
    player: Player.UI,
    modifier: Modifier = Modifier,
) {

    CardLayout(
        modifier = modifier,
        cardList = player.hand
    )

}

@Composable
fun CardLayout(
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
            val raiseX by animateFloatAsState(targetValue = if (isFocused) 25f else 0f, label = "scale")
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
                        } else TransformOrigin(0f, 1f)

                        translationY = raiseY
                        translationX = raiseX
                        rotationZ = cardAngle

                    }.scale(scale).zIndex(zIndex),
                interactionSource = interactionSource,
                enabled = enabled,
                card = card
            )
        }
    }

}