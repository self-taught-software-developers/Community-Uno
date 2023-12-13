package domain

import db.model.Collection
import db.model.Document
import dev.gitlive.firebase.firestore.FirebaseFirestore
import model.Card
import org.koin.core.component.KoinComponent

class GetCardFromDeckUseCase(
    private val fireStore: FirebaseFirestore
) : KoinComponent {
    suspend operator fun invoke(
        id: String,
        deck: List<Card>
    ) {
        if (id.isNotBlank()) {
            val drawnCard = deck.random().copy(ownerId = id)

            fireStore
                .collection(Collection.GameSession.name)
                .document(Document.GameDeck.name)
                .update(hashMapOf(drawnCard.uuid to drawnCard))
        }
    }
}