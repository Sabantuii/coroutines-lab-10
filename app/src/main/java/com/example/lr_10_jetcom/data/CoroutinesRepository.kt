package com.example.lr_10_jetcom.data

import kotlinx.coroutines.*

suspend fun simulateLongOperation(duration: Long): String {
    delay(duration)
    return "Операция завершена за $duration мс"
}

suspend fun calculateSum(numbers: List<Int>): Int {
    return withContext(Dispatchers.Default) {
        delay(1000)
        numbers.sum()
    }
}