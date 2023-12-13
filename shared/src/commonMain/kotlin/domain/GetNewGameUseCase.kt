package domain

import db.model.Collection
import db.model.Document
import dev.gitlive.firebase.firestore.DocumentReference
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.WriteBatch
import model.Card
import model.Player
import org.koin.core.component.KoinComponent

class GetNewGameUseCase(
    private val fireStore: FirebaseFirestore,
    private val getDeck: GetDeckUseCase
) : KoinComponent {
    suspend operator fun invoke(
        players: List<Player>,
        deck: List<Card> = getDeck.invoke()
    ) {
        val batch = fireStore.batch()
        batch.createANewDeck(deck, fireStore)
        batch.dealAndStartGame(deck, fireStore)

        val discardReference = fireStore
            .collection(Collection.GameSession.name)
            .document(Document.GameDiscard.name)

        batch.delete(discardReference)

        deck.forEach { card ->
            batch.set(
                documentRef = reference,
                data = hashMapOf(card.uuid to card),
                merge = true
            )
        }.also { batch.commit() }
    }

}

fun WriteBatch.createANewDeck(deck: List<Card>, fireStore: FirebaseFirestore) {

    val reference = fireStore
        .collection(Collection.GameSession.name)
        .document(Document.GameDeck.name)

    delete(reference)
    deck.forEach { card ->
        set(
            documentRef = reference,
            data = hashMapOf(card.uuid to card),
            merge = true
        )
    }
}
fun WriteBatch.dealAndStartGame(
    deck: List<Card>,
    players: List<Player>,
    handSize: Int = 7,
    fireStore: FirebaseFirestore
) {
    val discardReference = fireStore
        .collection(Collection.GameSession.name)
        .document(Document.GameDiscard.name)

    delete(discardReference)

    val distributedDeck = deck.shuffled().chunked(size = handSize)




}