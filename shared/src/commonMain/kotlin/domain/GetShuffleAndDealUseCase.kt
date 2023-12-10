package domain

import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.externals.FieldPath.Companion.documentId
import kotlinx.browser.document
import model.Card
import org.koin.core.component.KoinComponent

class GetShuffleAndDealUseCase(
    private val fireStore: FirebaseFirestore
) : KoinComponent {
    suspend operator fun invoke(deck: List<Card>) {
        /**
         * collect all active users and deal cards in accordance.
         * assign card to user.
         */
//        val referenceId = fireStore.collection("Game Session").document.id
//
//        val test = hashMapOf<String, List<Card>>()
//        test["deck"] = deck.shuffled()
//        fireStore
//            .collection("Game Session")
//            .document("Deck").set(test)


    }
}