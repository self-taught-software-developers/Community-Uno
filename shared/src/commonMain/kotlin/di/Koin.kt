package di

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import domain.GetAuthenticationUseCase
import domain.GetSessionUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(
    enableNetworkLogs: Boolean = false,
    appDeclaration: KoinAppDeclaration = { }
) = startKoin {
    appDeclaration()
    modules(remoteModule(enableNetworkLogs), domainModule())
}
fun initKoin() = initKoin(enableNetworkLogs = false) {}

fun remoteModule(enableNetworkLogs: Boolean) = module {
    single { CoroutineScope(Dispatchers.Default + SupervisorJob()) }
    single { Firebase.auth }
}

fun domainModule() = module {
    single { GetAuthenticationUseCase() }
    single { GetSessionUseCase() }
}