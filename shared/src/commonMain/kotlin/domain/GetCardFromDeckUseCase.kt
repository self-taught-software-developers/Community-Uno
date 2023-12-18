package domain

import db.model.Collection
import db.model.Document
import dev.gitlive.firebase.firestore.FirebaseFirestore
import model.Card
import org.koin.core.component.KoinComponent

class GetCardFromDeckUseCase(private val store: FirebaseFirestore) : KoinComponent {
    suspend operator fun invoke(
        id: String,
        deck: List<Card>
    ) {
        if (id.isNotBlank()) {
            val card = deck.random().copy(ownerId = id)

            store
                .collection(Collection.GameSession.name)
                .document(Document.GameDeck.name)
                .update(hashMapOf(card.key to card))
        }
    }
}