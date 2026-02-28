package com.simats.gokart.ui.screens

import androidx.compose.runtime.Composable
import com.simats.gokart.ui.components.TrackMap

@Composable
fun TrackScreen(vehicleType: String) {
    TrackMap(isFullScreen = true)
}