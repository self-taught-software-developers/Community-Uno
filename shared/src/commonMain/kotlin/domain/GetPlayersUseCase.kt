package domain

import db.model.Collection
import db.model.Document
import dev.gitlive.firebase.firestore.FirebaseFirestore
import helpers.observe
import model.Player
import org.koin.core.component.KoinComponent

class GetPlayersUseCase(
    private val fireStore: FirebaseFirestore
) : KoinComponent {

    operator fun invoke() = fireStore
        .collection(Collection.GameSession.name)
        .document(Document.ActivePlayers.name)
        .observe<List<Player>> { emptyList() }

}