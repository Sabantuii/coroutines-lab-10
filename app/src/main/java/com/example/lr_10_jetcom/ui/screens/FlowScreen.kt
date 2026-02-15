package com.example.lr_10_jetcom.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lr_10_jetcom.data.errorFlow
import com.example.lr_10_jetcom.data.numberFlow
import com.example.lr_10_jetcom.data.transformedFlow
import kotlinx.coroutines.launch

@Composable
fun FlowScreen() {
    var flowValues by remember { mutableStateOf<List<String>>(emptyList()) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = 16.dp)
        ) {
            items(flowValues) { value ->
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        Button(
            onClick = {
                flowValues = emptyList()
                scope.launch {
                    numberFlow().collect { value ->
                        flowValues = flowValues + "Число: $value"
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        ) {
            Text("Запустить Flow")
        }

        Button(
            onClick = {
                flowValues = emptyList()
                scope.launch {
                    transformedFlow(numberFlow()).collect { value ->
                        flowValues = flowValues + "Квадрат чётного: $value"
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        ) {
            Text("Запустить преобразованный Flow")
        }

        Button(
            onClick = {
                flowValues = emptyList()
                scope.launch {
                    errorFlow().collect { value ->
                        flowValues = flowValues + value
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        ) {
            Text("Запустить Flow с ошибкой")
        }
    }
}
@Preview
@Composable
fun FlowScreenPreview() {
    FlowScreen()
}