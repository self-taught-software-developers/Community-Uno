package domain

import db.model.Collection
import db.model.Document
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEmpty
import model.Profile
import org.koin.core.component.KoinComponent

class GetPlayersUseCase(
    private val fireStore: FirebaseFirestore
) : KoinComponent {

    operator fun invoke() = fireStore
        .collection(Collection.GameSession.name)
        .document(Document.ActivePlayers.name)
        .snapshots.map {
            try {
                it.data<List<Profile>>()
            } catch (e: Exception) {
                emptyList()
            }
        }

}