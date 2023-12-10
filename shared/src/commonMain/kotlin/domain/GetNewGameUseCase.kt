package domain

import db.model.Collection
import db.model.Document
import db.model.Field
import dev.gitlive.firebase.firestore.FirebaseFirestore
import model.Card
import org.koin.core.component.KoinComponent

class GetNewGameUseCase(
    private val fireStore: FirebaseFirestore,
    private val getDeck: GetDeckUseCase
) : KoinComponent {
    suspend operator fun invoke(deck: List<Card> = getDeck.invoke()) {

        val field = hashMapOf<String, List<Card>>().apply {
            this[Field.Deck.name] = deck
        }

        fireStore
            .collection(Collection.GameSession.name)
            .document(Document.GameDeck.name)
            .set(field)
    }


}