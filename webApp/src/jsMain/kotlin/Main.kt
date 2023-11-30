import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import di.initKoin
import domain.GetSessionUseCase
import kotlinx.coroutines.flow.Flow
import org.jetbrains.skiko.wasm.onWasmReady
import ui.MainScreen

private val koin = initKoin(enableNetworkLogs = true).koin

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    val counterTest = koin.get<GetSessionUseCase>()

    onWasmReady {
        CanvasBasedWindow(canvasElementId = "compose-canvas") {
            val state by produceState<String?>(null) {
                counterTest.invoke().collect { count ->
                    value = count
                }
            }

            MainScreen(state)
        }
    }
}