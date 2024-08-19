package com.example.bimber

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CategoriesScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Categories") },
                // Optionally add navigationIcon and actions here
            )
        },
        content = { paddingValues ->
            // Content of the screen, adjusting for padding if needed
            // Example content below
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Text("Under development")
            }
        }
    )
}
