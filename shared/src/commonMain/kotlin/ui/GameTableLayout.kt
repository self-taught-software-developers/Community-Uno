package ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import newmodel.Card
import newmodel.Player
import ui.component.PlayingCard
import ui.component.StyleGuide
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

@OptIn(ExperimentalLayoutApi::class)
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
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        DiscardPileLayout(
            modifier = Modifier,
            discardPile = discardPileList
        )
        DrawPileLayout(
            modifier = Modifier,
            drawPile = discardPileList,
            canDraw = activePlayer != null
        )
        if (activePlayer != null) {

            PlayerHandLayout(
                modifier = Modifier.fillMaxWidth(),
                player = activePlayer,
                isActivePlayer = true
            )

        }
    }

}

@Composable
fun PlayerHandLayout(
    player: Player.UI,
    modifier: Modifier = Modifier,
    angle: Double = 30.0,
    isActivePlayer: Boolean = false,
) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        player.hand.forEachIndexed { index, card ->
            val interactionSource = remember { MutableInteractionSource() }
            val isFocused by interactionSource.collectIsHoveredAsState()

            val scale by animateFloatAsState(targetValue = if (isFocused) 1.5f else 1f, label = "scale")
            val raiseY by animateFloatAsState(targetValue = if (isFocused) -50f else 0f, label = "scale")
            val raiseX by animateFloatAsState(targetValue = if (isFocused) 25f else 0f, label = "scale")
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
                        transformOrigin = if (isActivePlayer) {
                            TransformOrigin(0f, (angle/5).toFloat())
                        } else TransformOrigin(0f, 1f)
                        translationY = raiseY
                        translationX = raiseX
                        rotationZ = cardAngle
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

@Composable
fun DrawPileLayout(
    drawPile: List<Card.Entity>,
    modifier: Modifier = Modifier,
    canDraw: Boolean = false,
    angle: Float = 5f
) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        drawPile.forEachIndexed { index, card ->
            val cardAngle by remember {
                derivedStateOf {
                    (-angle / 2 + angle / (drawPile.count() + 1) * index)
                }
            }
            PlayingCard(
                modifier = Modifier.graphicsLayer {
                    translationX = cardAngle
                    translationY = cardAngle
                },
                enabled = canDraw,
                card = null
            )
        }
    }

}

@Composable
fun DiscardPileLayout(
    discardPile: List<Card.Entity>,
    modifier: Modifier = Modifier,
    angle: Float = 25f
) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        discardPile.indices.forEach { index ->
            val cardAngle by remember {
                derivedStateOf {
                    (-angle / 2 + angle / (discardPile.count() + 1) * index) + (1..100).random()
                }
            }
            PlayingCard(
                modifier = Modifier.rotate(cardAngle),
                enabled = false,
                card = null
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
    val starRadiusPx = with(LocalDensity.current) { radius.roundToPx() }

    var totalRadius by remember { mutableIntStateOf(0) }
    var maxChildDiameter by remember { mutableIntStateOf(0) }
    var count by remember { mutableIntStateOf(0) }

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
