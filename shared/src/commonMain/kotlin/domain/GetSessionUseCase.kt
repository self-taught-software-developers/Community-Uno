package domain

import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.where
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent

class GetSessionUseCase(
    private val authUseCase: GetAuthenticationUseCase,
    private val dbUseCase: GetDatabaseUseCase
) : KoinComponent {
    operator fun invoke() : Flow<String> = combine(
        authUseCase.invoke(),
        dbUseCase.invoke()
    ) { auth, db ->
        db.toString()
    }

}