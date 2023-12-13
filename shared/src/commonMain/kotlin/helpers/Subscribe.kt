package helpers

import com.arkivanov.essenty.lifecycle.LifecycleOwner
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.essenty.lifecycle.subscribe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

internal fun LifecycleOwner.whileStarted(context: CoroutineContext, block: suspend CoroutineScope.() -> Unit) {
    var scope: CoroutineScope? = null

    lifecycle.subscribe(
        onStart = {
            val newScope = CoroutineScope(context)
            scope = newScope
            newScope.launch(block = block)
        },
        onStop = {
            scope?.cancel()
            scope = null
        }
    )
}
