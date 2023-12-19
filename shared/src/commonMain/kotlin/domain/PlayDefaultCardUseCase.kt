package domain

import db.model.Collection
import db.model.Document
import db.model.Field
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.datetime.Clock
import model.Card
import model.GameDirection
import model.GameDirection.Companion.next
import model.Player
import org.koin.core.component.KoinComponent

class PlayDefaultCardUseCase(
    private val store: FirebaseFirestore,
) : KoinComponent {

    suspend operator fun invoke(
        card: Card,
        players: List<Player>,
        currentPlayer: Player,
        direction: GameDirection
    ) {

        store.batch().apply {

            val nextPlayer = currentPlayer.next(direction, players)

            val gDReference = store
                .collection(Collection.GameSession.name)
                .document(Document.GameData.name)

            set(
                documentRef = gDReference,
                data = hashMapOf(Field.CurrentPlayer.name to nextPlayer.id),
                merge = true
            )

            val dReference = store
                .collection(Collection.GameSession.name)
                .document(Document.GameDiscard.name)

            val discard = card.copy(playedAt = Clock.System.now().toEpochMilliseconds().toInt())

            set(
                documentRef = dReference,
                data = hashMapOf(card.key to discard),
                merge = true
            )

        }.commit()

    }

}