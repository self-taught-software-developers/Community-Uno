package domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent

class GetSessionUseCase(
    private val authUseCase: GetAuthenticationUseCase
) : KoinComponent {
    operator fun invoke() : Flow<String> = authUseCase.invoke().map { it?.uid.toString() }
}