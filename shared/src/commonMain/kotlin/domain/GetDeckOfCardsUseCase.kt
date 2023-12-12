package domain

import db.model.Collection
import db.model.Document
import dev.gitlive.firebase.firestore.FirebaseFirestore
import helpers.observe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import model.Card
import org.koin.core.component.KoinComponent

class GetDeckOfCardsUseCase(
    private val fireStore: FirebaseFirestore
) : KoinComponent {
    operator fun invoke(): Flow<List<Card>> = fireStore
        .collection(Collection.GameSession.name)
        .document(Document.GameDeck.name)
        .observe<List<Card>> { emptyList() }
}