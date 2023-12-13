package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import model.Card
import model.CardType

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GameTableScreen(
    modifier: Modifier,
    hand: List<Card>,
    onShuffle: () -> Unit,
    onNewGame: () -> Unit,
    onDrawCard: () -> Unit,
) {
    Column(modifier = modifier.fillMaxSize()){
        BoxWithConstraints(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                hand.forEachIndexed { index, card ->
                    Card(
                        modifier = Modifier
                            .size(width = 100.dp, height = 140.dp),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Box(
                            modifier = Modifier
                                .border(2.dp, Color.Black)
                                .padding(5.dp)
                                .border(2.dp, Color.Black)
                                .fillMaxSize()
                                .clip(MaterialTheme.shapes.medium)
                                .background(card.color.value)
                                .drawBehind {
                                    drawOval(
                                        color = Color.White,
                                        blendMode = BlendMode.Clear
                                    )
                                    drawOval(
                                        color = Color.Black,
                                        style = Stroke(width = 2f),
                                    )
                                },
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                modifier = Modifier.padding(5.dp),
                                text = card.name,
                                style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.ExtraBold),
                            )
                        }
                    }
                }
            }
//            Text(
//                """
//                type: ${deck.groupingBy { it.type }.eachCount()}
//                name: ${deck.groupingBy { it.name }.eachCount()}
//                color: ${deck.groupingBy { it.color }.eachCount()}
//                numeric: ${deck.groupingBy { it.type == CardType.Number }.eachCount()}
//                total: ${deck.count()}
//                """.trimIndent()
//            )
        }

        gameMasterTableScreen(
            onShuffleClick = onShuffle,
            onStartNewGameClick = onNewGame,
            onDrawCardClick = onDrawCard
        )
    }
}

@Composable
fun gameMasterTableScreen(
    onShuffleClick: () -> Unit,
    onStartNewGameClick: () -> Unit,
    onDrawCardClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ){
        Button(onClick = { onStartNewGameClick() }) {
            Text("New Game")
        }
        Button(onClick = { onShuffleClick() }) {
            Text("Shuffle")
        }
        Button(onClick = { onDrawCardClick() }) {
            Text("Draw")
        }
    }
}