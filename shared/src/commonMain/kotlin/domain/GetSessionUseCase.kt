package domain

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import db.model.Collection
import db.model.Document
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import model.CommunityUnoSession
import model.Player
import org.koin.core.component.KoinComponent

class GetSessionUseCase(
    private val store: FirebaseFirestore,
    private val authUseCase: GetAuthenticationUseCase,
    private val deckOfCards: GetDeckOfCardsUseCase,
    private val listOfPlayers: GetPlayersUseCase,
    private val discardPile: GetDiscardUseCase,
    private val direction: GetGameDirectionUseCase
) : KoinComponent {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke() : Flow<CommunityUnoSession> =
        authUseCase.invoke().flatMapConcat { id ->
            combine(
                deckOfCards.invoke(),
                listOfPlayers.invoke(),
                direction.invoke(),
                discardPile.invoke()
            ) { deck, players, data, discard ->

                val hand = deck.filter { card ->
                    card.ownerId == id && card.key !in discard.map { it.key }
                }
//                        it !in state.discard

                CommunityUnoSession(
                    id = id,
                    discard = discard,
                    deck = deck,
                    hand = hand,
                    isClockwise = data.isClockwise,
                    players = players,
                    playerId = data.currentPlayer
                )
            }.onStart {
                val player = Player(id = id, isAdmin = false)
                store.collection(Collection.GameSession.name)
                    .document(Document.ActivePlayers.name)
                    .set(data = hashMapOf(id to player), merge = true)
            }
        }
}