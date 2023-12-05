package di

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import domain.GetAuthenticationUseCase
import domain.GetDatabaseUseCase
import domain.GetSessionUseCase
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
    singleOf(::GetDatabaseUseCase)
    singleOf(::GetSessionUseCase)
}