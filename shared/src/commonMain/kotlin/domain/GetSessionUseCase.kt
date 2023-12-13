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
    private val direction: GetGameDirectionUseCase
) : KoinComponent {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke() : Flow<CommunityUnoSession> =
        flow {
            emit(
                CommunityUnoSession(
                    id = "testId",
                    deck = GetDeckUseCase().invoke().map { it.copy(ownerId = "${(0..7).random()}") },
                    players = List(7) { Player("$it") },
                    playerId = "2"
                )
            )
        }
//        authUseCase.invoke().flatMapConcat {id ->
//            combine(
//                deckOfCards.invoke(),
//                listOfPlayers.invoke(),
//                direction.invoke()
//            ) { deck, players, data ->
//                CommunityUnoSession(
//                    id = id,
//                    deck = deck,
//                    isClockwise = data.isClockwise,
//                    players = players,
//                    playerId = data.currentPlayer
//                )
//            }.onStart {
//                val player = Player(id = id, isActive = true, isAdmin = false)
//                firebase.collection(Collection.GameSession.name)
//                    .document(Document.ActivePlayers.name)
//                    .set(data = hashMapOf(id to player), merge = true)
//            }
//        }
}