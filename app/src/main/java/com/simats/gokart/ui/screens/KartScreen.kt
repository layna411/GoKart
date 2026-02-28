package com.simats.gokart.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.simats.gokart.ui.components.ModelViewer

@Composable
fun KartScreen(vehicleType: String) {
    val modelName = if (vehicleType == "ev") "Ev_Kart.glb" else "Petrol_Kart.glb"
    Box(modifier = Modifier.fillMaxSize()) {
        ModelViewer(modelName = modelName, modifier = Modifier.fillMaxSize())
    }
}