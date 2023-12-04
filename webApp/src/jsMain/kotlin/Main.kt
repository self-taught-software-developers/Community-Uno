import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseOptions
import dev.gitlive.firebase.initialize
import di.initKoin
import domain.GetSessionUseCase
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.skiko.wasm.onWasmReady
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

    val counterTest = koin.get<GetSessionUseCase>()

    onWasmReady {
        CanvasBasedWindow(canvasElementId = "compose-canvas") {
            val state by produceState<String?>(null) {
                counterTest.invoke().collectLatest { count ->
                    value = count
                }
            }

            MainScreen(state)
        }
    }
}