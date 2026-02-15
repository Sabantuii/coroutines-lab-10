package com.example.lr_10_jetcom.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.lr_10_jetcom.data.riskyFlow
import com.example.lr_10_jetcom.data.riskyOperation
import com.example.lr_10_jetcom.data.safeOperation
import kotlinx.coroutines.launch

@Composable
fun ErrorHandlingScreen() {
    var result by remember { mutableStateOf<String?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        result?.let {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFA5D6A7) // Green80
                )
            ) {
                Text(
                    text = it,
                    modifier = Modifier.padding(16.dp),
                    color = Color(0xFF004D19) // OnGreen80
                )
            }
        }

        errorMessage?.let {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Text(
                    text = "Ошибка: $it",
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        }

        Button(
            onClick = {
                result = null
                errorMessage = null
                scope.launch {
                    try {
                        val res = riskyOperation(true)
                        result = res
                    } catch (e: Exception) {
                        errorMessage = e.message
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        ) {
            Text("Успешная операция (try-catch)")
        }

        Button(
            onClick = {
                result = null
                errorMessage = null
                scope.launch {
                    try {
                        val res = riskyOperation(false)
                        result = res
                    } catch (e: Exception) {
                        errorMessage = e.message ?: "Неизвестная ошибка"
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        ) {
            Text("Операция с ошибкой (try-catch)")
        }

        Button(
            onClick = {
                result = null
                errorMessage = null
                scope.launch {
                    riskyFlow().collect { value ->
                        if (value.contains("Ошибка")) {
                            errorMessage = value.replace("Ошибка обработана: ", "")
                        } else {
                            result = value
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        ) {
            Text("Flow с обработкой ошибок (catch)")
        }

        Button(
            onClick = {
                result = null
                errorMessage = null
                scope.launch {
                    val safeResult = safeOperation(false)
                    safeResult.fold(
                        onSuccess = { result = it },
                        onFailure = { errorMessage = it.message ?: "Неизвестная ошибка" }
                    )
                }
            },
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        ) {
            Text("Безопасная операция (Result)")
        }
    }
}