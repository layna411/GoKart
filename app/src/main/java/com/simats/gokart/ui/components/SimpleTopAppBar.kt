package com.simats.gokart.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.simats.gokart.ui.theme.BgCharcoal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleTopAppBar() {
    TopAppBar(
        title = { Text("Gokart") },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = BgCharcoal,
            titleContentColor = Color.White
        )
    )
}