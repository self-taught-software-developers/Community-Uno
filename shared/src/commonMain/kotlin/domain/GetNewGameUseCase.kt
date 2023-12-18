package domain

import db.model.Collection
import db.model.Document
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.WriteBatch
import model.Card
import model.CardType
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
        fireStore.batch().apply {

            val sessionReference = fireStore.collection(Collection.GameSession.name)
            val deckReference = sessionReference.document(Document.GameDeck.name).also(::delete)
            val discardReference = sessionReference.document(Document.GameDiscard.name).also(::delete)

            delete(deckReference)
            delete(discardReference)

            val modifiedDeck = deck.dealCards(players)
            val discardCard = modifiedDeck.first { card ->
                card.ownerId == null &&
                        card.type == CardType.Number

            }
            val deckAfterDiscard = modifiedDeck
                .filter { card -> card != discardCard }
                .toData()

            set(documentRef = discardReference, data = discardCard.toData(), merge = true)

            deckAfterDiscard.forEach { data ->
                set(documentRef = deckReference, data = data, merge = true)
            }
        }.commit()

    }

}

fun List<Card>.dealCards(
    players: List<Player>,
    handSize: Int = 7
) : List<Card> {
    return shuffled()
        .chunked(handSize)
        .mapIndexed { index, cards ->
            val owner = players.getOrNull(index)

            cards.map { card ->
                card.copy(ownerId = owner?.id)
            }
        }.flatten()
}

fun List<Card>.toData(): List<HashMap<String,Card>> {
    return map { hashMapOf(it.uuid to it) }
}
fun Card.toData(): HashMap<String, Card> {
    return hashMapOf(uuid to this)
}
