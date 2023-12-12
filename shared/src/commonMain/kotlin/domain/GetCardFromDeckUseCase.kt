package domain

import db.model.Collection
import db.model.Document
import db.model.Field
import dev.gitlive.firebase.firestore.FieldValue
import dev.gitlive.firebase.firestore.FirebaseFirestore
import model.Card
import org.koin.core.Koin
import org.koin.core.component.KoinComponent

class GetCardFromDeckUseCase(
    private val fireStore: FirebaseFirestore
) : KoinComponent {
    suspend operator fun invoke(
        id: String,
        deck: List<Card>
    ) {

        if (id.isNotBlank()) {
            val drawnCard = deck.random()
            val index = deck.indexOf(drawnCard)
            val modifiedDeck: MutableList<Card> = deck.toMutableList()
            modifiedDeck[index] = drawnCard.copy(ownerId = id)

            fireStore
                .collection(Collection.GameSession.name)
                .document(Document.GameDeck.name)
                .update(hashMapOf(Field.Deck.name to modifiedDeck))
        }
    }
}