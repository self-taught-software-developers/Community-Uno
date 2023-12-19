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

class PlayReverseGameDirectionUseCase(
    private val store: FirebaseFirestore
) : KoinComponent {

    suspend operator fun invoke(
        card: Card,
        players: List<Player>,
        currentPlayer: Player,
        direction: GameDirection
    ) {

        store.batch().apply {

            val nextPlayer = currentPlayer
                .next(direction, players)

            val gDReference = store
                .collection(Collection.GameSession.name)
                .document(Document.GameData.name)

            val playerData = hashMapOf(Field.CurrentPlayer.name to nextPlayer.id)

            set(
                documentRef = gDReference,
                data = playerData,
                merge = true
            )

            val directionData = hashMapOf(Field.GameDirection.name to direction.opposite())

            set(
                documentRef = gDReference,
                data = directionData,
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