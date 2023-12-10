package domain

import model.Card
import org.koin.core.Koin
import org.koin.core.component.KoinComponent

class GetCardFromDeckUseCase(

) : KoinComponent {
    suspend operator fun invoke(
        id: String,
        card: Card
    ) {

    }
}