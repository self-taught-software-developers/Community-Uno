package domain

import db.model.Collection
import db.model.Document
import db.model.Field
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.datetime.Clock
import model.Card
import model.GameDirection
import model.GameDirection.Companion.next
import model.Player
import org.koin.core.component.KoinComponent

class PlayDrawCardMultipleUseCase(
    private val store: FirebaseFirestore
) : KoinComponent {

    /**
     * Used as a draw2 and draw4 call by modifying the repetition count.
     */
    suspend operator fun invoke(
        repetition: Int,
        card: Card,
        deck: List<Card>,
        players: List<Player>,
        currentPlayer: Player,
        direction: GameDirection
    ) {

        store.batch().apply {
            val nextPlayer = currentPlayer.next(direction, players)

            val cPReference = store
                .collection(Collection.GameSession.name)
                .document(Document.GameData.name)

            set(
                documentRef = cPReference,
                data = hashMapOf(Field.CurrentPlayer.name to nextPlayer.id),
                merge = true
            )

            val deckReference = store
                .collection(Collection.GameSession.name)
                .document(Document.GameDeck.name)

            val cards = mutableListOf<Card>()

            repeat(repetition) {
                cards.add(deck.random().copy(ownerId = nextPlayer.id))
            }

            cards.forEach { card ->
                set(
                    documentRef = deckReference,
                    data = hashMapOf(card.key to card),
                    merge = true
                )
            }

            val dReference = store
                .collection(Collection.GameSession.name)
                .document(Document.GameDiscard.name)

            val discard = card.copy(playedAt = Clock.System.now().toEpochMilliseconds().toInt())

            set(
                documentRef = dReference,
                data = hashMapOf(card.key to discard),
                merge = true
            )
        }.commit()

    }
}