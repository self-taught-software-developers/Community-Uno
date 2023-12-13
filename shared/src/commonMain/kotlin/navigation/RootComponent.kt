package navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.Value
import domain.GetAuthenticationUseCase
import helpers.coroutineScope
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import model.CommunityUnoSession
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface RootComponent {

    val stack: Value<ChildStack<*, RootChild>>

    sealed class RootChild {
        data object Loading: RootChild()
        data class Game(val component: GameComponent): RootChild()
    }
}

class DefaultRootComponent(
    componentContext: ComponentContext
) : RootComponent, KoinComponent, ComponentContext by componentContext {

    private val coroutineScope = coroutineScope()
    private val authentication: GetAuthenticationUseCase by inject()
    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, RootComponent.RootChild>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = Config.Loading,
            childFactory = ::child
        )

    init {
        coroutineScope.launch {
            authentication.invoke()
                .distinctUntilChanged()
                .collect(::onAuthenticated)
        }
    }

    private fun onAuthenticated(uid: String) {
        navigation.navigate { backstack ->
            backstack.map { config ->
                when(config) {
                    is Config.Game -> {
                        config.copy(uid = uid)
                    }
                    else -> config
                }
            }
        }
    }

    private fun child(
        config: Config,
        context: ComponentContext
    ) : RootComponent.RootChild = when (config) {
        is Config.Loading -> RootComponent.RootChild.Loading
        is Config.Game -> RootComponent.RootChild.Game(
            DefaultGameComponent(
                componentContext = context
            )
        )
    }


    @Serializable
    sealed interface Config {
        @Serializable
        data object Loading: Config

        @Serializable
        data class Game(val uid: String): Config
    }

}