package com.example.lr_10_jetcom.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import com.example.lr_10_jetcom.data.*
import kotlinx.coroutines.*

@Composable
fun CoroutinesScreen() {
    var isLoading by remember { mutableStateOf(false) }
    var result by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        }

        result?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp)
            )
        }

        Button(
            onClick = {
                isLoading = true
                result = null
                scope.launch {
                    val res = simulateLongOperation(2000)
                    result = res
                    isLoading = false
                }
            },
            enabled = !isLoading,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text("Запустить долгую операцию")
        }

        Button(
            onClick = {
                isLoading = true
                result = null
                scope.launch {
                    val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                    val sum = calculateSum(numbers)
                    result = "Сумма чисел: $sum"
                    isLoading = false
                }
            },
            enabled = !isLoading,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text("Вычислить сумму")
        }
    }
}