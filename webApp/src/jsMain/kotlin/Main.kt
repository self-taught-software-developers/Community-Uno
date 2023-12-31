import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.CanvasBasedWindow
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseOptions
import dev.gitlive.firebase.initialize
import di.initKoin
import domain.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import model.CommunityUnoSession
import org.jetbrains.skiko.wasm.onWasmReady
import ui.GameTableScreen
import ui.component.PlayingCard

private val koin = initKoin(enableNetworkLogs = true).koin
private val firebaseInit = Firebase.initialize(
    options = FirebaseOptions(
        apiKey= "AIzaSyBKItvS2Jr8kAYsuJAPCrS6zMvtFmns2W0",
        authDomain= "community-uno-kmp.firebaseapp.com",
        projectId= "community-uno-kmp",
        storageBucket= "community-uno-kmp.appspot.com",
        applicationId= "1:743664290283:web:3afcfc28906780bb6631ae"
    )
)

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    firebaseInit

    val session = koin.get<GetSessionUseCase>()

    onWasmReady {
        CanvasBasedWindow(canvasElementId = "compose-canvas") {

            val uiState by produceState<CommunityUnoSession?>(null) {
                session.invoke().collectLatest { session ->
                    value = session
                }
            }

            val scope = rememberCoroutineScope()

            uiState?.let { state ->
                Scaffold(
                    bottomBar = {
                        Column {
                            Divider()
                            BottomAppBar {
                                if (state.player().isAdmin) {
                                    Button(onClick = {
                                        scope.launch {
                                            koin.get<GetNewGameUseCase>().invoke(players = state.players)
                                        }
                                    }) {
                                        Text("New Game")
                                    }

                                    Button(onClick = {
                                        scope.launch {
                                            koin.get<GetNextPlayerUseCase>().invoke(
                                                currentPlayer = state.currentPlayer(),
                                                direction = state.gameDirection,
                                                players = state.players,
                                            )
                                        }
                                    }) {
                                        Text("Next Player")
                                    }
                                }
                            }
                        }
                    }
                ) {
                    GameTableScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it),
                        state = state,
                        onPlayCard = { card ->
                            scope.launch {
                                koin.get<GetPlayCardUseCase>().invoke(
                                    card = card,
                                    currentPlayer = state.currentPlayer(),
                                    deck = state.deck,
                                    direction = state.gameDirection,
                                    players = state.players
                                )
                            }
                        },
                        onDrawCard = {
                            scope.launch {
                                koin.get<DrawCardSingleUseCase>().invoke(
                                    currentPlayer = state.currentPlayer(),
                                    deck = state.deck,
                                    direction = state.gameDirection,
                                    players = state.players,
                                    playerId = state.playerId
                                )
                            }
                        }
                    )
                }
            }


//            uiState?.let { state ->
//                val useAbleDeck by remember(state) {
//                    derivedStateOf {
//                        state.deck.filter { it.ownerId == null }
//                    }
//                }
//
//                val scope = rememberCoroutineScope()
//
//                Column(
//                    modifier = Modifier.background(
//                        color = state.globalColor()
//                    )
//                ) {
//                    Text(state.id)
//                    Text("deck size: ${state.deck.size}")
//                    Text("usable deck size: ${useAbleDeck.size}")
//                    Text("discard size: ${state.discard.size}")
//                    Text("hand size: ${state.hand.size}")
//                    Text("Player count: ${state.players.size}")
//                    Text("Current Player Id: ${state.playerId}")
//                    Text("Direction: ${state.isClockwise}")
//
//                    GameTableScreen(
//                        modifier = Modifier.weight(1f),
//                        discard = state.discard,
//                        hand = state.hand,
//                        isPlayerTurn = state.playerId == state.id,
//                        onPlayCard = { card ->
//                            scope.launch {
//                                koin.get<GetPlayCardUseCase>().invoke(
//                                    card = card,
//                                    state.players,
//                                    state.playerId,
//                                    state.isClockwise
//                                )
//                            }
//                        },
//                        onNewGame = {
//                            scope.launch {
//                                koin.get<GetNewGameUseCase>().invoke(players = state.players)
//                            }
//                        },
//                        onDrawCard = {
//                            scope.launch {
//                                koin.get<GetCardFromDeckUseCase>().invoke(
//                                    state.id,
//                                    state.deck,
//                                    state.players,
//                                    state.playerId,
//                                    state.isClockwise
//                                )
//                            }
//                        }
//                    )
//                }
//            }

        }
    }
}