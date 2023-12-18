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

        Box(
            modifier = Modifier.fillMaxSize().weight(1f, fill = true),
            contentAlignment = Alignment.Center
        ) {
            discard.forEach { card ->
                val orientation by remember { mutableFloatStateOf((0..180).random().toFloat()) }
                PlayingCard(
                    modifier = Modifier.rotate(orientation),
                    card = card
                )
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
        Button(onClick = { onShuffleClick() }) {
            Text("Shuffle")
        }
        Button(onClick = { onDrawCardClick() }) {
            Text("Draw")
        }
    }
}