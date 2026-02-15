package com.example.lr_10_jetcom.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun StateFlowScreen() {
    val counterState = remember { mutableStateOf(0) }
    val isAutoIncrementing = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var autoIncrementJob by remember { mutableStateOf<Job?>(null) }

    fun increment() {
        counterState.value += 1
    }

    fun decrement() {
        counterState.value -= 1
    }

    fun reset() {
        counterState.value = 0
    }

    fun incrementBy(value: Int) {
        counterState.value += value
    }

    fun toggleAutoIncrement() {
        if (isAutoIncrementing.value) {
            isAutoIncrementing.value = false
            autoIncrementJob?.cancel()
            autoIncrementJob = null
        } else {
            isAutoIncrementing.value = true
            autoIncrementJob = scope.launch {
                while (true) {
                    delay(1000)
                    counterState.value += 1
                }
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            autoIncrementJob?.cancel()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = counterState.value.toString(),
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier.padding(16.dp)
        )

        if (isAutoIncrementing.value) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                CircularProgressIndicator(modifier = Modifier.size(16.dp))
                Text("Автоинкремент активен", modifier = Modifier.padding(start = 8.dp))
            }
        }

        Row(modifier = Modifier.padding(vertical = 8.dp)) {
            Button(onClick = { decrement() }) {
                Text("-1")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { increment() }) {
                Text("+1")
            }
        }

        Button(
            onClick = { reset() },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text("Сброс")
        }

        Button(
            onClick = { incrementBy(5) },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text("+5")
        }

        Button(
            onClick = { toggleAutoIncrement() },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text(if (isAutoIncrementing.value) "Остановить автоинкремент" else "Запустить автоинкремент")
        }
    }
}