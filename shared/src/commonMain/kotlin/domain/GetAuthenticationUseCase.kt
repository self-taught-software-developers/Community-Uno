package domain

import dev.gitlive.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.onEach
import org.koin.core.component.KoinComponent

class GetAuthenticationUseCase(
    private val auth: FirebaseAuth
) : KoinComponent {
    operator fun invoke() = auth.authStateChanged.onEach {
        if (it == null) {
            auth.signInAnonymously()
        }
    }
}