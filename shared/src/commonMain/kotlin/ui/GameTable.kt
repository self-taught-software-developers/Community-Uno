package ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import model.Card
import ui.component.PlayingCard

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GameTableScreen(
    modifier: Modifier,
    isPlayerTurn: Boolean,
    discard: List<Card>,
    hand: List<Card>,
    onPlayCard: (Card) -> Unit,
    onShuffle: () -> Unit,
    onNewGame: () -> Unit,
    onDrawCard: () -> Unit,
) {
    Column(modifier = modifier.fillMaxSize()){

        Row(
            modifier = Modifier.fillMaxSize().weight(1f).padding(paddingValues = PaddingValues(20.dp)),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                discard.forEach { card ->
                    val orientation by remember { mutableFloatStateOf((-60..60).random().toFloat()) }
                    PlayingCard(
                        modifier = Modifier.rotate(orientation),
                        card = card
                    )
                }
            }
            PlayingCard(modifier = Modifier, enabled = isPlayerTurn) {
                onDrawCard()
            }
        }

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            hand.forEach { card ->
                PlayingCard(card, enabled = isPlayerTurn) { onPlayCard(card) }
            }
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
    }
}