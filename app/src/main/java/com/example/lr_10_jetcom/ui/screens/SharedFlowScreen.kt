package com.example.lr_10_jetcom.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun SharedFlowScreen() {
    val eventsSharedFlow = remember { MutableSharedFlow<String>(replay = 3) }
    val eventsFlow = eventsSharedFlow.asSharedFlow()
    var events by remember { mutableStateOf<List<String>>(emptyList()) }
    var eventCount by remember { mutableStateOf(0) }
    var eventCounter by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()
    var autoGenerationJob by remember { mutableStateOf<Job?>(null) }
    var isAutoGenerating by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        eventsFlow.collect { event ->
            events = (events + event).takeLast(10)
            eventCount++
        }
    }

    fun emitEvent(message: String) {
        scope.launch {
            eventsSharedFlow.emit(message)
        }
    }

    fun startAutoGeneration() {
        if (autoGenerationJob?.isActive == true) return
        isAutoGenerating = true
        autoGenerationJob = scope.launch {
            while (true) {
                delay(2000)
                eventCounter++
                val randomNumber = Random.nextInt(1, 101)
                emitEvent("Событие #$eventCounter: $randomNumber")
            }
        }
    }

    fun stopAutoGeneration() {
        isAutoGenerating = false
        autoGenerationJob?.cancel()
        autoGenerationJob = null
    }


    DisposableEffect(Unit) {
        onDispose {
            autoGenerationJob?.cancel()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Всего событий: $eventCount",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = 16.dp)
        ) {
            items(events.reversed()) { event ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Text(
                        text = event,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        }

        Button(
            onClick = {
                emitEvent("Ручное событие #${eventCount + 1}")
            },
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        ) {
            Text("Сгенерировать событие")
        }

        Button(
            onClick = {
                if (isAutoGenerating) {
                    stopAutoGeneration()
                } else {
                    startAutoGeneration()
                }
            },
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        ) {
            Text(if (isAutoGenerating) "Остановить автогенерацию" else "Запустить автогенерацию")
        }
    }
}