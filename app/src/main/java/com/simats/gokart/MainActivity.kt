package com.simats.gokart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.simats.gokart.ui.components.BottomNavigationBar
import com.simats.gokart.ui.navigation.Screen
import com.simats.gokart.ui.screens.DashboardScreen
import com.simats.gokart.ui.screens.DetailsScreen
import com.simats.gokart.ui.screens.KartScreen
import com.simats.gokart.ui.screens.SplashScreen
import com.simats.gokart.ui.screens.TrackScreen
import com.simats.gokart.ui.screens.VehicleSelectionScreen
import com.simats.gokart.ui.theme.GokartTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GokartTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != "vehicleSelection" && currentRoute?.startsWith("splash/") == false) {
                BottomNavigationBar(navController = navController)
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = "vehicleSelection",
            modifier = Modifier.padding(it)
        ) {
            composable("vehicleSelection") {
                VehicleSelectionScreen(navController)
            }
            composable("splash/{vehicleType}", arguments = listOf(navArgument("vehicleType") { type = NavType.StringType })) {
                SplashScreen(navController, it.arguments?.getString("vehicleType") ?: "ev")
            }
            composable("dashboard/{vehicleType}", arguments = listOf(navArgument("vehicleType") { type = NavType.StringType })) {
                DashboardScreen(navController = navController, vehicleType = it.arguments?.getString("vehicleType") ?: "ev")
            }
            composable("main/{vehicleType}", arguments = listOf(navArgument("vehicleType") { type = NavType.StringType })) {
                DashboardScreen(navController = navController, vehicleType = it.arguments?.getString("vehicleType") ?: "ev")
            }
            composable("kart/{vehicleType}", arguments = listOf(navArgument("vehicleType") { type = NavType.StringType })) {
                KartScreen(it.arguments?.getString("vehicleType") ?: "ev")
            }
            composable("details/{vehicleType}", arguments = listOf(navArgument("vehicleType") { type = NavType.StringType })) {
                DetailsScreen(it.arguments?.getString("vehicleType") ?: "ev")
            }
            composable("track/{vehicleType}", arguments = listOf(navArgument("vehicleType") { type = NavType.StringType })) {
                TrackScreen(it.arguments?.getString("vehicleType") ?: "ev")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GokartTheme {
        MainScreen()
    }
}