package domain

import db.model.Collection
import db.model.Document
import db.model.Field
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.externals.collection
import kotlinx.datetime.Clock
import model.Card
import model.Player

class GetPlayCardUseCase(
    private val store: FirebaseFirestore,
) {

    suspend operator fun invoke(
        card: Card,
        players: List<Player>,
        currentPlayerId: String?,
        gameDirection: Boolean
    ) {

        store.batch().apply {

            val next = getNextPlayer(
                players = players,
                currentPlayerId = currentPlayerId,
                gameDirection = gameDirection
            )

            val cPReference = store.collection(Collection.GameSession.name)
                .document(Document.GameData.name)

            set(
                documentRef = cPReference,
                data = hashMapOf(Field.CurrentPlayer.name to next.id),
                merge = true
            )

            val discardReference = store.collection(Collection.GameSession.name)
                .document(Document.GameDiscard.name)

            set(
                documentRef = discardReference,
                data = hashMapOf(card.key to card.copy(playedAt = Clock.System.now().toEpochMilliseconds().toInt())),
                merge = true
            )

        }.commit()

    }

    companion object {
        fun getNextPlayer(
            players: List<Player>,
            currentPlayerId: String?,
            gameDirection: Boolean
        ) : Player {

            val currentPlayer = players.first { it.id == currentPlayerId }
            val indexOfPlayer = players.indexOf(currentPlayer)

            return if (gameDirection) {
                val nextIndex = indexOfPlayer + 1
                if (nextIndex > players.lastIndex) {
                    players.first()
                } else { players[nextIndex] }
            } else {
                val nextIndex = indexOfPlayer - 1
                if (nextIndex < 0) {
                    players.last()
                } else { players[nextIndex] }
            }

        }
    }
}