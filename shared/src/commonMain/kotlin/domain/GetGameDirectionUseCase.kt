package domain

import db.model.Collection
import db.model.Document
import dev.gitlive.firebase.firestore.FirebaseFirestore
import helpers.observe
import kotlinx.coroutines.flow.Flow
import model.GameData
import org.koin.core.component.KoinComponent

class GetGameDirectionUseCase(
    private val fireStore: FirebaseFirestore
): KoinComponent {

    //TODO READ IS FAILING
    operator fun invoke(): Flow<GameData> = fireStore
        .collection(Collection.GameSession.name)
        .document(Document.GameData.name)
//        .snapshots.map { it.data<GameDirection>() }
        .observe<GameData> { GameData() }
}