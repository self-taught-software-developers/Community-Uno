package helpers

import dev.gitlive.firebase.firestore.DocumentReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

inline fun <reified T: Any> DocumentReference.observe(
    crossinline fallback: () -> T
) : Flow<T> {
    return snapshots.map { snapshot ->
        try {
            snapshot.data<T>()
        } catch (e: Exception) { fallback() }
    }
}