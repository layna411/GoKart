package com.simats.gokart.ui.navigation

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object Kart : Screen("kart")
    object Details : Screen("details")
    object Track : Screen("track")
}