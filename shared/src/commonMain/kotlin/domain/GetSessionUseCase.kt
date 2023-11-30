package domain

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import kotlin.time.Duration.Companion.seconds

class GetSessionUseCase : KoinComponent {
    operator fun invoke() : Flow<String> = flow {
        var index = 0
        while (index <= 100) {
            emit(index.toString())
            delay(1.seconds)
            index++
        }
    }
}