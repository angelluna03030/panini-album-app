package com.angel.panini

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.angel.panini.ui.navigation.Screen
import com.angel.panini.ui.navigation.bottomNavScreens
import com.angel.panini.ui.screens.exchange.ExchangeScreen
import com.angel.panini.ui.screens.obtained.ObtainedScreen
import com.angel.panini.ui.screens.pending.PendingScreen
import com.angel.panini.ui.screens.repeated.RepeatedScreen
import com.angel.panini.ui.screens.search.SearchScreen
import com.angel.panini.ui.theme.PaniniAlbumTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PaniniAlbumTheme {
                val navController = rememberNavController()
                val backStack by navController.currentBackStackEntryAsState()
                val currentRoute = backStack?.destination?.route

                Scaffold(
                    bottomBar = {
                        NavigationBar(
                            containerColor = androidx.compose.ui.graphics.Color(0xFF1A1A1A),
                            tonalElevation  = androidx.compose.ui.unit.Dp(0f)
                        ) {
                            bottomNavScreens.forEach { screen ->
                                NavigationBarItem(
                                    selected = currentRoute == screen.route,
                                    onClick  = {
                                        navController.navigate(screen.route) {
                                            popUpTo(navController.graph.startDestinationId) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState    = true
                                        }
                                    },
                                    icon  = { Icon(screen.icon, contentDescription = screen.label) },
                                    label = { Text(screen.label, style = MaterialTheme.typography.labelSmall) },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor   = com.angel.panini.ui.theme.GoldFIFA,
                                        selectedTextColor   = com.angel.panini.ui.theme.GoldFIFA,
                                        unselectedIconColor = com.angel.panini.ui.theme.TextTertiary,
                                        unselectedTextColor = com.angel.panini.ui.theme.TextTertiary,
                                        indicatorColor      = com.angel.panini.ui.theme.Surface2,
                                    )
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController    = navController,
                        startDestination = Screen.Obtained.route,
                        modifier         = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Obtained.route)  { ObtainedScreen() }
                        composable(Screen.Pending.route)   { PendingScreen() }
                        composable(Screen.Repeated.route)  { RepeatedScreen() }
                        composable(Screen.Exchange.route)  { ExchangeScreen() }
                        composable(Screen.Search.route)    { SearchScreen() }
                    }
                }
            }
        }
    }
}