import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.rememberCoroutineScope
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
//            val state by produceState<String?>(null) {
//                counterTest.invoke().collectLatest { count ->
//                    value = count
//                }
//            }
//            MainScreen(state)

            val state by produceState(
                CommunityUnoSession(id = "")
            ) {
                session.invoke().collectLatest { session ->
                    value = session
                }
            }
            val scope = rememberCoroutineScope()

            GameTableScreen(
                deck = state.deck,
                onNewGame = {
                    scope.launch {
                        koin.get<GetNewGameUseCase>().invoke()
                    }
                },
                onShuffle = {
                    scope.launch {
                        koin.get<GetShuffleAndDealUseCase>().invoke(state.deck)
                    }
                },
                onDrawCard = {
                    scope.launch {
                        koin.get<GetCardFromDeckUseCase>().invoke(
                            state.id,
                            state.deck.random()
                        )
                    }
                }
            )
        }
    }
}