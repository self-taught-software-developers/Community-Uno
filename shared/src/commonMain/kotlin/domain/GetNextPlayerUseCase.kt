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

class GetNextPlayerUseCase(
    private val store: FirebaseFirestore
) : KoinComponent {

    suspend operator fun invoke(
        players: List<Player>,
        currentPlayer: Player,
        direction: GameDirection
    ) {

        store.batch().apply {

            val nextPlayer = currentPlayer
                .next(direction, players)
                .next(direction, players)

            val gDReference = store
                .collection(Collection.GameSession.name)
                .document(Document.GameData.name)

            set(
                documentRef = gDReference,
                data = hashMapOf(Field.CurrentPlayer.name to nextPlayer.id),
                merge = true
            )
        }.commit()

    }


}