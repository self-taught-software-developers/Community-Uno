package domain

import db.model.Collection
import db.model.Document
import dev.gitlive.firebase.firestore.FirebaseFirestore
import helpers.observe
import model.Card

class GetDiscardUseCase(
    private val fireStore: FirebaseFirestore
) {
    operator fun invoke() = fireStore
        .collection(Collection.GameSession.name)
        .document(Document.GameDiscard.name)
        .observe<List<Card>> { emptyList() }
}