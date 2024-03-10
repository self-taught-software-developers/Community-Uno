import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseOptions
import dev.gitlive.firebase.initialize
import di.initKoin
import newmodel.mock.cardUiListMock
import newmodel.mock.playerUiListMock
import newmodel.mock.playerUiMock
import org.jetbrains.skiko.wasm.onWasmReady
import ui.screen.GameSessionScreen

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

            GameSessionScreen(
                player = playerUiMock(),
                opponentList = playerUiListMock(),
                drawList = cardUiListMock(),
                discardList = cardUiListMock().map { it.copy(isFaceShown = true) },
            )

        }
    }
}