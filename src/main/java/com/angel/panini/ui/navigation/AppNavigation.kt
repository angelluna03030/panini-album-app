package com.angel.panini.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Obtained  : Screen("obtained",   "Obtenidas",   Icons.Outlined.CheckCircle)
    object Pending   : Screen("pending",    "Pendientes",  Icons.Outlined.Star)
    object Repeated  : Screen("repeated",   "Repetidas",   Icons.Outlined.Add)
    object Exchange  : Screen("exchange",   "Intercambio", Icons.Outlined.Refresh)
    object Search    : Screen("search",     "Buscar",      Icons.Outlined.Search)
}

val bottomNavScreens = listOf(
    Screen.Obtained,
    Screen.Pending,
    Screen.Repeated,
    Screen.Exchange,
    Screen.Search,
)