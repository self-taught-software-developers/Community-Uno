package domain

import db.model.Collection
import db.model.Document
import db.model.Field
import dev.gitlive.firebase.firestore.FirebaseFirestore
import model.Card
import model.GameDirection
import model.GameDirection.Companion.next
import model.Player
import org.koin.core.component.KoinComponent

class DrawCardSingleUseCase(private val store: FirebaseFirestore) : KoinComponent {
    suspend operator fun invoke(
        playerId: String,
        deck: List<Card>,
        players: List<Player>,
        currentPlayer: Player,
        direction: GameDirection
    ) {
        store.batch().apply {
            val nextPlayer = currentPlayer.next(direction, players)

            val cPReference = store.collection(Collection.GameSession.name)
                .document(Document.GameData.name)

            set(
                documentRef = cPReference,
                data = hashMapOf(Field.CurrentPlayer.name to nextPlayer.id),
                merge = true
            )

            val dReference = store
                .collection(Collection.GameSession.name)
                .document(Document.GameDeck.name)

            val card = deck.random().copy(ownerId = playerId)

            set(
                documentRef = dReference,
                data = hashMapOf(card.key to card),
                merge = true
            )
        }.commit()
    }
}