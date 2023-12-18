package domain

import db.model.Collection
import db.model.Document
import dev.gitlive.firebase.firestore.FirebaseFirestore
import helpers.observe
import model.Player
import org.koin.core.component.KoinComponent

class GetPlayersUseCase(
    private val store: FirebaseFirestore
) : KoinComponent {
    operator fun invoke() = store
        .collection(Collection.GameSession.name)
        .document(Document.ActivePlayers.name)
        .observe<List<Player>> { emptyList() }

}