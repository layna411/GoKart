package com.simats.gokart.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.simats.gokart.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController, vehicleType: String) {
    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate("dashboard/$vehicleType") { // Pass vehicleType to dashboard
            popUpTo("splash/{vehicleType}") {
                inclusive = true
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        val imageRes = if (vehicleType == "ev") R.drawable.women else R.drawable.men
        Image(painter = painterResource(id = imageRes), contentDescription = "Splash Image")
    }
}