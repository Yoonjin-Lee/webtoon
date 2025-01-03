package yj.myapplication

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun main() = runBlocking {
    val sharedFlow = MutableSharedFlow<Int>(replay = 1)

    launch {
        sharedFlow.emit(1)
        delay(100)
        sharedFlow.emit(2)
    }

    launch {
        sharedFlow.collect { value ->
            println("Collector 1 received: $value")
        }
    }

    delay(50)

    launch {
        sharedFlow.collect { value ->
            println("Collector 2 received: $value")
        }
    }

    delay(200)
}