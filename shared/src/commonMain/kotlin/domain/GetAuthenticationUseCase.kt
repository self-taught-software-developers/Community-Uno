package domain

import dev.gitlive.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.internal.JSJoda.use
import org.koin.core.component.KoinComponent

class GetAuthenticationUseCase(
    private val auth: FirebaseAuth
) : KoinComponent {
    operator fun invoke() : Flow<String> = auth.authStateChanged
        .onEach { user ->
            if (user == null) {
                auth.signInAnonymously()
            }
        }.mapNotNull{ it?.uid }
}