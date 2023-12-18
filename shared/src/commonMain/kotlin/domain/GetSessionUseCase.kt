package domain

import db.model.Collection
import db.model.Document
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import model.CommunityUnoSession
import model.Player
import org.koin.core.component.KoinComponent

class GetSessionUseCase(
    private val firebase: FirebaseFirestore,
    private val authUseCase: GetAuthenticationUseCase,
    private val deckOfCards: GetDeckOfCardsUseCase,
    private val listOfPlayers: GetPlayersUseCase,
    private val discardPile: GetDiscardUseCase,
    private val direction: GetGameDirectionUseCase
) : KoinComponent {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke() : Flow<CommunityUnoSession> =
        authUseCase.invoke().flatMapConcat {id ->
            combine(
                deckOfCards.invoke(),
                listOfPlayers.invoke(),
                direction.invoke(),
                discardPile.invoke()
            ) { deck, players, data, discard ->
                CommunityUnoSession(
                    id = id,
                    discard = discard,
                    deck = deck,
                    isClockwise = data.isClockwise,
                    players = players,
                    playerId = data.currentPlayer
                )
            }.onStart {
                val player = Player(id = id, isAdmin = false)
                firebase.collection(Collection.GameSession.name)
                    .document(Document.ActivePlayers.name)
                    .set(data = hashMapOf(id to player), merge = true)
            }
        }
}