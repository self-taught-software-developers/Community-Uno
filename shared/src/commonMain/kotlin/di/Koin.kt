package di

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import domain.GetAuthenticationUseCase
import domain.GetDeckOfCardsUseCase
import domain.GetSessionUseCase
import domain.GetDeckUseCase
import domain.GetShuffleAndDealUseCase
import domain.GetNewGameUseCase
import domain.GetCardFromDeckUseCase
import domain.SetGameDirectionUseCase
import domain.GetGameDirectionUseCase
import domain.GetPlayersUseCase
import domain.GetNextPlayerUseCase
import domain.GetDiscardUseCase
import domain.GetPlayCardUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(
    enableNetworkLogs: Boolean = false,
    appDeclaration: KoinAppDeclaration = { }
) = startKoin {
    appDeclaration()
    modules(remoteModule(enableNetworkLogs), domainModule())
}
fun initKoin() = initKoin { }

fun remoteModule(enableNetworkLogs: Boolean) = module {
    single { CoroutineScope(Dispatchers.Default + SupervisorJob()) }
    single { Firebase.auth }
    single { Firebase.firestore }
}

fun domainModule() = module {
    singleOf(::GetAuthenticationUseCase)
    singleOf(::GetDeckOfCardsUseCase)
    singleOf(::GetSessionUseCase)
    singleOf(::GetDeckUseCase)
    singleOf(::GetShuffleAndDealUseCase)
    singleOf(::GetNewGameUseCase)
    singleOf(::GetCardFromDeckUseCase)
    singleOf(::GetPlayersUseCase)
    singleOf(::SetGameDirectionUseCase)
    singleOf(::GetGameDirectionUseCase)
    singleOf(::GetNextPlayerUseCase)
    singleOf(::GetDiscardUseCase)
    singleOf(::GetPlayCardUseCase)
}