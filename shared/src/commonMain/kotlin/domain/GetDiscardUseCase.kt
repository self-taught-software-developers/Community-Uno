package domain

import db.model.Collection
import db.model.Document
import dev.gitlive.firebase.firestore.FirebaseFirestore
import helpers.observe
import kotlinx.coroutines.flow.mapNotNull
import model.Card

class GetDiscardUseCase(
    private val store: FirebaseFirestore
) {
    operator fun invoke() = store
        .collection(Collection.GameSession.name)
        .document(Document.GameDiscard.name)
        .observe<List<Card>> { emptyList() }.mapNotNull { it.sortedBy { it.playedAt } }
}