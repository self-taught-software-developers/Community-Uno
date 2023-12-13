package domain

import db.model.Collection
import db.model.Document
import dev.gitlive.firebase.firestore.FirebaseFirestore
import model.Card
import org.koin.core.component.KoinComponent

class GetNewGameUseCase(
    private val fireStore: FirebaseFirestore,
) : KoinComponent {
    suspend operator fun invoke(
        deck: List<Card> = generateDeck()
    ) {
        val batch = fireStore.batch()
        val reference = fireStore
            .collection(Collection.GameSession.name)
            .document(Document.GameDeck.name)

        batch.delete(reference)

        deck.forEach { card ->
            batch.set(
                documentRef = reference,
                data = hashMapOf(card.uuid to card),
                merge = true
            )
        }.also { batch.commit() }
    }

}