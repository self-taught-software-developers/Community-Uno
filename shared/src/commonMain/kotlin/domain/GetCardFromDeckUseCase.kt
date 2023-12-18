package domain

import db.model.Collection
import db.model.Document
import db.model.Field
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.datetime.Clock
import model.Card
import model.Player
import org.koin.core.component.KoinComponent

class GetCardFromDeckUseCase(private val store: FirebaseFirestore) : KoinComponent {
    suspend operator fun invoke(
        id: String,
        deck: List<Card>,
        players: List<Player>,
        currentPlayerId: String?,
        gameDirection: Boolean
    ) {
        store.batch().apply {
            val next = GetPlayCardUseCase.getNextPlayer(
                players = players,
                currentPlayerId = currentPlayerId,
                gameDirection = gameDirection
            )

            val cPReference = store.collection(Collection.GameSession.name)
                .document(Document.GameData.name)

            set(
                documentRef = cPReference,
                data = hashMapOf(Field.CurrentPlayer.name to next.id),
                merge = true
            )

            if (id.isNotBlank()) {
                val card = deck.random().copy(ownerId = id)

                val reference = store
                    .collection(Collection.GameSession.name)
                    .document(Document.GameDeck.name)

                set(
                    documentRef = reference,
                    data = hashMapOf(card.key to card),
                    merge = true
                )
            }
        }.commit()
    }
}