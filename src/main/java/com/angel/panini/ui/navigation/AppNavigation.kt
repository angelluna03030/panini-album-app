package com.angel.panini.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.angel.panini.ui.screens.ExchangeScreen
import com.angel.panini.ui.screens.HomeScreen
import com.angel.panini.ui.screens.ObtainedScreen
import com.angel.panini.ui.screens.PendingScreen
import com.angel.panini.ui.screens.PlayerDetailScreen
import com.angel.panini.ui.screens.RegisterScreen
import com.angel.panini.ui.screens.RepeatedScreen

sealed class Screen(val route: String, val title: String, val icon: @Composable () -> Unit) {
    object Home : Screen("home", "Inicio", { Icon(Icons.Default.Home, contentDescription = "Inicio") })
    object Obtained : Screen("obtained", "Obtenidas", { Icon(Icons.Default.CheckCircle, contentDescription = "Obtenidas") })
    object Pending : Screen("pending", "Pendientes", { Icon(Icons.Default.Schedule, contentDescription = "Pendientes") })
    object Repeated : Screen("repeated", "Repetidas", { Icon(Icons.Default.SwapHoriz, contentDescription = "Repetidas") })
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val screens = listOf(Screen.Home, Screen.Obtained, Screen.Pending, Screen.Repeated)

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                screens.forEach { screen ->
                    NavigationBarItem(
                        icon = screen.icon,
                        label = { Text(screen.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    onNavigateToRegister = { navController.navigate("register") },
                    onNavigateToExchange = { navController.navigate("exchange") }
                )
            }
            composable(Screen.Obtained.route) {
                ObtainedScreen(
                    onPlayerClick = { playerName ->
                        navController.navigate("player/${playerName.replace(" ", "%20")}")
                    }
                )
            }
            composable(Screen.Pending.route) {
                PendingScreen(
                    onPlayerClick = { playerName ->
                        navController.navigate("player/${playerName.replace(" ", "%20")}")
                    }
                )
            }
            composable(Screen.Repeated.route) {
                RepeatedScreen(
                    onPlayerClick = { playerName ->
                        navController.navigate("player/${playerName.replace(" ", "%20")}")
                    }
                )
            }
            composable("register") {
                RegisterScreen(onNavigateBack = { navController.popBackStack() })
            }
            composable("exchange") {
                ExchangeScreen(onNavigateBack = { navController.popBackStack() })
            }
            composable(
                route = "player/{playerName}",
                arguments = listOf(navArgument("playerName") { type = NavType.StringType })
            ) { backStackEntry ->
                val playerName = backStackEntry.arguments?.getString("playerName")?.replace("%20", " ") ?: ""
                PlayerDetailScreen(
                    playerName = playerName,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}