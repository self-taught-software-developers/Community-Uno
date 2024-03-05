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
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.zIndex
import newmodel.Card
import newmodel.Player
import ui.component.PlayingCard
import ui.component.StyleGuide
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

@Composable
fun GameTableLayout(
    activePlayer: Player.UI?,
    otherPlayerList: List<Player.UI>,
    drawPileList: List<Card.Entity>,
    discardPileList: List<Card.Entity>,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(StyleGuide.xPadding.value),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BoxWithConstraints(
            modifier = Modifier.weight(3f),
            contentAlignment = Alignment.Center
        ) {

            StarLayout(
                radius = maxWidth / 4F
            ) {
                otherPlayerList.forEachIndexed { index, player ->
                    PlayerHandLayout(
                        player = player
                    )
                }
            }

        }

        if (activePlayer != null) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                PlayerHandLayout(
                    player = activePlayer,
                    isActivePlayer = true
                )
            }
        }
    }

}

@Composable
fun PlayerHandLayout(
    player: Player.UI,
    modifier: Modifier = Modifier,
    angle: Double = 45.0,
    isActivePlayer: Boolean = false,
    spacing: Int = if (isActivePlayer) 60 else 10
) {

    Box(modifier = modifier) {
        player.hand.forEachIndexed { index, card ->
            val interactionSource = remember { MutableInteractionSource() }
            val isFocused by interactionSource.collectIsHoveredAsState()

            val scale by animateFloatAsState(targetValue = if (isFocused) 1.2f else 1f, label = "scale")
            val zIndex by animateFloatAsState(targetValue = if (isFocused) 1f else 0f, label = "zIndex")

            val cardAngle by remember {
                derivedStateOf {
                    ((-angle / 2 + angle / (player.hand.count() + 1) * index)
                            ).toFloat()
                }
            }

            PlayingCard(
                modifier = Modifier
                    .graphicsLayer {
                        transformOrigin = TransformOrigin.Center
                        translationX = (index * spacing).toFloat()
                        rotationZ = cardAngle
    //                    rotationX = -cardAngle
                        rotationY = -cardAngle

                    }.scale(scale).zIndex(zIndex),
                interactionSource = interactionSource,
                enabled = isActivePlayer,
                card = if (isActivePlayer) {
                    card
                } else null
            )
        }
    }

}

fun toRadians(deg: Double): Double = deg / 180.0 * PI

fun toDegrees(rad: Double): Double = rad * 180.0 / PI

@Composable
fun StarLayout(
    radius: Dp,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    var starRadiusPx = with(LocalDensity.current) { radius.roundToPx() }
    var totalRadius = 0
    var maxChildDiameter = 0
    var count = 0

    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        val placeables = measurables.map { it.measure(constraints) }
        count = placeables.size
        placeables.forEach {
            val h = it.height.toDouble()
            val w = it.width.toDouble()
            val diameter = sqrt(h * h + w * w)
            if (diameter > maxChildDiameter) maxChildDiameter = diameter.toInt()
        }
        totalRadius = starRadiusPx + maxChildDiameter / 2
        layout(totalRadius * 2, totalRadius * 2) {
            val step = PI * 2 / count
            var angle = 0.0
            placeables.forEach { placeable ->
                placeable.place(
                    totalRadius - placeable.width / 2 + (starRadiusPx * cos(angle)).toInt(),
                    totalRadius - placeable.height / 2 + (starRadiusPx * sin(angle)).toInt(),
                )
                angle += step
            }
        }
    }
}
