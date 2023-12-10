package domain

import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.mapNotNull
import model.Card
import org.koin.core.component.KoinComponent

class GetUnAssignedCardsUseCase(
    private val fireStore: FirebaseFirestore
) : KoinComponent {
    operator fun invoke() = fireStore
        .collection("Game Session")
        .document("Deck")
        .snapshots.mapNotNull { snapshot ->
            try {
                snapshot.data<Map<String, List<Card>>>()
                    .values
                    .firstOrNull()
            } catch (e: Exception) { null }
        }
}