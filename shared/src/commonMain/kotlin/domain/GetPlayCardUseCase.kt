package domain

import db.model.Collection
import db.model.Document
import db.model.Field
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.externals.collection
import model.Card
import model.Player

class GetPlayCardUseCase(
    private val fireStore: FirebaseFirestore,
    private val nextPlayCardUseCase: GetNextPlayerUseCase
) {

    suspend operator fun invoke(
        card: Card,
        players: List<Player>,
        currentPlayerId: String?,
        gameDirection: Boolean
    ) {

        fireStore.batch().apply {

            val next = nextPlayCardUseCase.invoke(
                players = players,
                currentPlayerId = currentPlayerId,
                gameDirection = gameDirection
            )

            val cPReference = fireStore.collection(Collection.GameSession.name)
                .document(Document.GameData.name)

            set(
                documentRef = cPReference,
                data = hashMapOf(Field.CurrentPlayer.name to next.id),
                merge = true
            )

            val discardReference = fireStore.collection(Collection.GameSession.name)
                .document(Document.GameDiscard.name)

            set(
                documentRef = discardReference,
                data = hashMapOf(card.uuid to card),
                merge = true
            )

        }.commit()

    }
}