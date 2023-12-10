package ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import model.Card
import model.CardType

@Composable
fun GameTableScreen(
    deck: List<Card>,
    onShuffle: () -> Unit,
    onNewGame: () -> Unit,
    onDrawCard: () -> Unit,
) {
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("""
            type: ${deck.groupingBy { it.type }.eachCount()}
            name: ${deck.groupingBy { it.name }.eachCount()}
            color: ${deck.groupingBy { it.color }.eachCount()}
            numeric: ${deck.groupingBy { it.type == CardType.Number }.eachCount()}
            total: ${deck.count()}
        """.trimIndent())
    }
    Column(modifier = Modifier.fillMaxSize()){
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