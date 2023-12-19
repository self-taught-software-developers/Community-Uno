package ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.onClick
import androidx.compose.material.Badge
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import model.Card
import model.CardColor
import model.CardType
import model.CommunityUnoSession
import ui.component.PlayingCard

@OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
@Composable
fun GameTableScreen(
    modifier: Modifier,
    state: CommunityUnoSession?,
    discard: List<Card> = emptyList(),
    onSelectPlayer: (String) -> Unit = { },
    onPlayCard: (Card) -> Unit = { },
    onNewGame: () -> Unit = { },
    onDrawCard: () -> Unit = { },
) {
    state?.let {

        Column(modifier = modifier.fillMaxSize()) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Players")

                FlowRow(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    state.players.forEach { player ->
                        val count by remember { derivedStateOf { state.deck.filter { it.ownerId == player.id }.size } }

                        Box(
                            modifier = Modifier.onClick { onSelectPlayer(player.id) },
                            contentAlignment = Alignment.BottomEnd
                        ) {

                            Icon(
                                modifier = Modifier.size(64.dp),
                                imageVector = Icons.Default.Person,
                                contentDescription = null
                            )
                            Badge(
                                backgroundColor = if (state.isPlayersTurn(player.id)) {
                                    Color.Green
                                } else { Color.White }
                            ) {
                                Text(
                                    modifier = Modifier.padding(5.dp),
                                    text = "$count"
                                )
                            }
                        }

                    }
                }
            }

            Row(modifier = Modifier.weight(2f)) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .weight(3f),
                    contentAlignment = Alignment.Center
                ) {
                    state.discard.forEach { card ->
                        val orientation by remember { mutableFloatStateOf((-60..60).random().toFloat()) }
                        val placement by remember { mutableIntStateOf((1..5).random()) }

                        PlayingCard(
                            modifier = Modifier
                                .rotate(orientation)
                                .offset(x = placement.dp, placement.dp),
                            card = card,
                            enabled = false
                        )
                    }
                }

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(modifier = Modifier.onClick { onDrawCard() }) {
                        state.deck.forEachIndexed { index, card ->
                            PlayingCard(modifier = Modifier.offset(x = (index * 0.1).dp, y = (index * 0.1).dp)) {

                            }
                        }
                    }
                    Text("click to draw")
                }
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                var wildCard by remember(state.hand) { mutableStateOf<Card?>(null) }
                val showColor by remember { derivedStateOf { wildCard != null } }

                AnimatedVisibility(showColor) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CardColor.entries.filter { it != CardColor.BLACK }.forEach { color ->
                            Box(modifier = Modifier.size(100.dp).background(color.value).onClick {
                                onPlayCard(wildCard!!.copy(color = color))
                            })
                        }
                    }
                }

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    state.hand.forEach { card ->
                        PlayingCard(card) {
                            when (card.type) {
                                CardType.Wild, CardType.Draw4 -> {
                                    wildCard = card
                                }

                                else -> {
                                    onPlayCard(card)
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}
