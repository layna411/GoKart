package com.simats.gokart.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Hardware
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.simats.gokart.ui.navigation.Screen

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        Screen.Main to Icons.Filled.GridView,
        Screen.Kart to Icons.Filled.Hardware,
        Screen.Track to Icons.Filled.Map,
        Screen.Details to Icons.Filled.Settings
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { (screen, icon) ->
            NavigationBarItem(
                icon = { Icon(icon, contentDescription = screen.route) },
                label = { Text(screen.route.replaceFirstChar { it.uppercase() }) },
                selected = currentRoute?.startsWith(screen.route) == true,
                onClick = {
                    val vehicleType = navBackStackEntry?.arguments?.getString("vehicleType") ?: "ev"
                    val route = "${screen.route}/$vehicleType"
                    navController.navigate(route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.startDestinationId)
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}