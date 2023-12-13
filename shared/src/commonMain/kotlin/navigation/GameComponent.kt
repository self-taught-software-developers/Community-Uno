package navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import domain.GetSessionUseCase
import helpers.asValue
import kotlinx.coroutines.flow.map
import model.CommunityUnoSession
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

interface GameComponent {
    val uiState: Value<UiState>

    sealed interface UiState
    data object Loading: UiState
    data object Error: UiState
    data class Success(val session: CommunityUnoSession) : UiState
}
class DefaultGameComponent(
    componentContext: ComponentContext,
): GameComponent, KoinComponent, ComponentContext by componentContext {

    override val uiState = get<GetSessionUseCase>()
        .invoke().map { GameComponent.Success(it) }
        .asValue(GameComponent.Loading, lifecycle)

}