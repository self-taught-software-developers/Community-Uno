package domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import model.Card
import model.CommunityUnoSession
import org.koin.core.component.KoinComponent

class GetSessionUseCase(
    private val authUseCase: GetAuthenticationUseCase,
    private val unAssignedCards: GetUnAssignedCardsUseCase
) : KoinComponent {
    operator fun invoke() : Flow<CommunityUnoSession> = combine(
        authUseCase.invoke(),
        unAssignedCards.invoke()
    ) { authId, unAssignedCards ->
        CommunityUnoSession(
            id = authId,
            deck = unAssignedCards
        )
    }

}