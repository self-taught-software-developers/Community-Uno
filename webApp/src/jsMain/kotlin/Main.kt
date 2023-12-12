import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseOptions
import dev.gitlive.firebase.initialize
import di.initKoin
import domain.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import model.Card
import model.CommunityUnoSession
import org.jetbrains.skiko.wasm.onWasmReady
import ui.GameTableScreen
import ui.MainScreen

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

            val state by produceState(
                CommunityUnoSession(id = "")
            ) {
                session.invoke().collect { session ->
                    value = session
                }
            }
            val useAbleDeck by remember(state) {
                derivedStateOf {
                    state.deck.filter { it.ownerId == null }
                }
            }
            val hand by remember(state) { derivedStateOf {
                state.deck.filter { it.ownerId == state.id }
            } }
            val scope = rememberCoroutineScope()

            Column {
                Text(state.id)
                Text("deck size: ${state.deck.size}")
                Text("usable deck size: ${useAbleDeck.size}")
                Text("hand size: ${hand.size}")
                Text("Player count: ${state.players.size}")

                GameTableScreen(
                    deck = hand,
                    onNewGame = {
                        scope.launch {
                            koin.get<GetNewGameUseCase>().invoke()
                        }
                    },
                    onShuffle = {
                        scope.launch {

                        }
                    },
                    onDrawCard = {
                        scope.launch {
                            koin.get<GetCardFromDeckUseCase>().invoke(
                                state.id,
                                state.deck
                            )
                        }
                    }
                )
                Row {
                    Button(onClick = { }) {
                        Text("Create Game")
                    }

                    Button(onClick = { }) {
                        Text("Play Game")
                    }
                }
            }

        }
    }
}