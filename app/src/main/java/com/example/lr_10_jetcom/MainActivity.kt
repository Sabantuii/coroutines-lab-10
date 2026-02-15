package com.example.lr_10_jetcom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import com.example.lr_10_jetcom.ui.screens.CoroutinesScreen
import com.example.lr_10_jetcom.ui.screens.ErrorHandlingScreen
import com.example.lr_10_jetcom.ui.screens.FlowScreen
import com.example.lr_10_jetcom.ui.screens.SharedFlowScreen
import com.example.lr_10_jetcom.ui.screens.StateFlowScreen
import com.example.lr_10_jetcom.ui.theme.LR_10_JetComTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LR_10_JetComTheme {

                LR_10_App()

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LR_10_App() {
    var currentScreen by remember { mutableStateOf("coroutines") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Лабораторная 10.2: Корутины и Flow") }
            )
        },
        bottomBar = {
            NavigationBar {
                listOf(
                    "coroutines" to "Корутины",
                    "flow" to "Flow",
                    "stateflow" to "StateFlow",
                    "sharedflow" to "SharedFlow",
                    "errors" to "Ошибки"
                ).forEach { (screenId, label) ->
                    NavigationBarItem(
                        selected = currentScreen == screenId,
                        onClick = { currentScreen = screenId },
                        icon = { /* можно добавить иконки */ },
                        label = { Text(label) }
                    )
                }
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (currentScreen) {
                "coroutines" -> CoroutinesScreen()
                "flow" -> FlowScreen()
                "stateflow" -> StateFlowScreen()
                "sharedflow" -> SharedFlowScreen()
                "errors" -> ErrorHandlingScreen()
            }
        }
    }
}