package domain

import db.model.Collection
import db.model.Document
import db.model.Field
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.*
import model.CommunityUnoSession
import model.Profile
import org.koin.core.component.KoinComponent

class GetSessionUseCase(
    private val firebase: FirebaseFirestore,
    private val authUseCase: GetAuthenticationUseCase,
    private val deckOfCards: GetDeckOfCardsUseCase
) : KoinComponent {

    private lateinit var userAuthId: String
    private var isFirstRun = true

    operator fun invoke() : Flow<CommunityUnoSession> = combine(
        authUseCase.invoke(),
        deckOfCards.invoke()
    ) { authId, fullDeck ->
        userAuthId = authId
        CommunityUnoSession(
            id = authId,
            deck = fullDeck
        )
    }.onEach {
        if (isFirstRun) {
            firebase.collection(Collection.GameSession.name)
                .document(Document.ActivePlayers.name)
                .set(hashMapOf(Field.Player.name to Profile(
                    id = userAuthId,
                    isActive = true
                )))
            isFirstRun = false
        }
    }
//        .onCompletion {
//        firebase.collection(Collection.GameSession.name)
//            .document(Document.ActivePlayers.name)
//            .set(hashMapOf(Field.PlayerId.name to Profile(
//                id = userAuthId,
//                isActive = false
//            )))
//    }

}