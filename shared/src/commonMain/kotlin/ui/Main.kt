package ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import navigation.RootComponent

@Composable
fun App(
    component: RootComponent
) {
    Children(
        stack = component.stack,
    ) {
        when(val child = it.instance) {
            is RootComponent.RootChild.Loading -> LoadingView()
            is RootComponent.RootChild.Game -> {

                Text("Game")
            }
        }
    }

//    val session by koin.get<ScreenAComponent>().session.subscribeAsState()
//    val session = koin.get<GetSessionUseCase>()
//    val uiState by produceState<CommunityUnoSession?>(null) {
//        session.invoke().collectLatest { session ->
//            value = session
//        }
//    }

//    session.let { state ->
//
//        val useAbleDeck by remember(state) {
//            derivedStateOf {
//                state.deck.filter { it.ownerId == null }
//            }
//        }
//        val hand by remember(state) {
//            derivedStateOf {
//                state.deck.filter { it.ownerId == state.id }
//            }
//        }
//        val scope = rememberCoroutineScope()
//
//        Column {
//            Text(state.id)
//            Text("deck size: ${state.deck.size}")
//            Text("usable deck size: ${useAbleDeck.size}")
//            Text("hand size: ${hand.size}")
//            Text("Player count: ${state.players.size}")
//            Text("Current Player Id: ${state.playerId}")
//            Text("Direction: ${state.isClockwise}")
//
//            GameTableScreen(
//                modifier = Modifier.weight(1f),
//                deck = hand,
//                onNewGame = {
//                    scope.launch {
//                        koin.get<GetNewGameUseCase>().invoke()
//                    }
//                },
//                onShuffle = {
//                    scope.launch {
//
//                    }
//                },
//                onDrawCard = {
//                    scope.launch {
//                        koin.get<GetCardFromDeckUseCase>().invoke(
//                            state.id,
//                            state.deck
//                        )
//                    }
//                }
//            )
//            Row(modifier = Modifier.fillMaxWidth()) {
//                Button(
//                    onClick = {
//                        scope.launch {
//                            koin.get<SetGameDirectionUseCase>()
//                                .invoke(state.isClockwise)
//                        }
//                    }
//                ) {
//                    Text("Reverse")
//                }
//                Button(onClick = {
//                    scope.launch {
//                        koin.get<GetNextPlayerUseCase>()
//                            .invoke(state.players, state.playerId, state.isClockwise)
//                    }
//                }) {
//                    Text("Next")
//                }
//
//            }
//        }
//    }
}

@Composable
fun LoadingView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .wrapContentSize(Alignment.Center)
    ) {
        CircularProgressIndicator()
    }
}