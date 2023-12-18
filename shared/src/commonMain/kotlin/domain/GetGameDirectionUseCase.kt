package domain

import db.model.Collection
import db.model.Document
import dev.gitlive.firebase.firestore.FirebaseFirestore
import helpers.observe
import kotlinx.coroutines.flow.Flow
import model.GameData
import org.koin.core.component.KoinComponent

class GetGameDirectionUseCase(
    private val store: FirebaseFirestore
): KoinComponent {

    operator fun invoke(): Flow<GameData> = store
        .collection(Collection.GameSession.name)
        .document(Document.GameData.name)
        .observe<GameData> { GameData() }
}