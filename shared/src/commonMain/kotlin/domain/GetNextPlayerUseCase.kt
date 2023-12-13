package domain

import db.model.Collection
import db.model.Document
import db.model.Field
import dev.gitlive.firebase.firestore.FirebaseFirestore
import model.Player
import org.koin.core.component.KoinComponent

class GetNextPlayerUseCase(
    private val fireStore: FirebaseFirestore
) : KoinComponent {
    suspend operator fun invoke(
        players: List<Player>,
        currentPlayerId: String?,
        gameDirection: Boolean,
        jump: Int = 1,
    ) {

        val currentPlayer = players.first { it.id == currentPlayerId }
        val indexOfPlayer = players.indexOf(currentPlayer)

        val nextPlayer = if (gameDirection) {
            val nextIndex = indexOfPlayer + jump
            if (nextIndex > players.lastIndex) {
                players.first()
            } else { players[nextIndex] }
        } else {
            val nextIndex = indexOfPlayer - jump
            if (nextIndex < 0) {
                players.last()
            } else { players[nextIndex] }
        }

        fireStore
            .collection(Collection.GameSession.name)
            .document(Document.GameData.name).set(
                data = hashMapOf(Field.CurrentPlayer.name to nextPlayer.id),
                merge = true
            )

    }
}