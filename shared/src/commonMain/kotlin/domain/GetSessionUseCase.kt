package domain

import db.model.Collection
import db.model.Document
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import model.*
import org.koin.core.component.KoinComponent

class GetSessionUseCase(
    private val store: FirebaseFirestore,
    private val authUseCase: GetAuthenticationUseCase,
    private val deckOfCards: GetDeckOfCardsUseCase,
    private val listOfPlayers: GetPlayersUseCase,
    private val discardPile: GetDiscardUseCase,
    private val gameData: GetGameDirectionUseCase
) : KoinComponent {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke() : Flow<CommunityUnoSession> =
//        flow {
//            emit(
//                CommunityUnoSession(
//                    discard = listOf(Card(type = CardType.Wild, color = CardColor.RED)),
//                    deck = getDeck(),
//                    hand = listOf(Card(type = CardType.Wild, color = CardColor.BLACK)),
//                    playerId = "testing 1",
//                    currentPlayerId = "testing 1",
//                    players = List(9) { (Player("testing $it")) }
//                )
//            )
//        }
        authUseCase.invoke().flatMapConcat { id ->
            combine(
                deckOfCards.invoke(),
                listOfPlayers.invoke(),
                gameData.invoke(),
                discardPile.invoke()
            ) { deck, players, data, discard ->

                val hand = deck.filter { card ->
                    card.ownerId == id && card.key !in discard.map { it.key }
                }
                CommunityUnoSession(
                    playerId = id,
                    discard = discard,
                    deck = deck,
                    hand = hand,
                    gameDirection = data.gameDirection,
                    players = players,
                    currentPlayerId = data.currentPlayer
                )
            }.onStart {
                val player = Player(id = id, isAdmin = false)
                store.collection(Collection.GameSession.name)
                    .document(Document.ActivePlayers.name)
                    .set(data = hashMapOf(id to player), merge = true)
            }
        }
}