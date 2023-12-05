package domain

import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.mapNotNull
import model.Card
import org.koin.core.component.KoinComponent

class GetDatabaseUseCase(
    private val fireStore: FirebaseFirestore
) : KoinComponent {
    operator fun invoke() = fireStore.collection("Game Session")
        .snapshots.mapNotNull { snapshot ->
            try {
                snapshot.documents.map { documentSnapshot ->
                    documentSnapshot.data<Card>()
                }
            } catch (e: Exception) { null }
        }
}