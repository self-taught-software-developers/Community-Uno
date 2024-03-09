import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
import newmodel.Card
import newmodel.Player
import newmodel.mock.cardUiListMock
import newmodel.mock.playerUiListMock
import newmodel.mock.playerUiMock
import org.jetbrains.skiko.wasm.onWasmReady
import ui.GameTableLayout
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

    onWasmReady {
        CanvasBasedWindow(canvasElementId = "compose-canvas") {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .defaultMinSize(1000.dp, 1000.dp) // Set minimum size
            ) {
                GameTableLayout(
                    activePlayer = playerUiMock(),
                    otherPlayerList = playerUiListMock(),
                    discardPileList = cardUiListMock().map { it.copy(isFaceShown = true) },
                    drawPileList = cardUiListMock()
                )
            }
        }
    }
}