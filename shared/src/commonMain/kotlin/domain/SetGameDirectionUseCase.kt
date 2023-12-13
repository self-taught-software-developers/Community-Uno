package domain

import db.model.Collection
import db.model.Document
import db.model.Field
import dev.gitlive.firebase.firestore.FirebaseFirestore
import org.koin.core.component.KoinComponent

class SetGameDirectionUseCase(
    private val store: FirebaseFirestore
) : KoinComponent {

    suspend operator fun invoke(isClockwise: Boolean) {
        store.collection(Collection.GameSession.name)
            .document(Document.GameData.name).set(
                data = hashMapOf(Field.GameDirection.name to !isClockwise),
                merge = true
            )
    }
}