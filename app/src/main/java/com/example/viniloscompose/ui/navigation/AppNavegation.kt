package com.example.viniloscompose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.viniloscompose.ui.screens.AlbumScreen
import com.example.viniloscompose.ui.screens.CollectorScreen
import com.example.viniloscompose.ui.screens.InicioScreen
import com.example.viniloscompose.ui.screens.MusicianScreen


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreens.InicioScreen.route) {
        composable(route = AppScreens.InicioScreen.route) {
            InicioScreen(onNavigate = {destination -> navController.navigate(destination)})
        }
        composable(route = AppScreens.MusicianScreen.route) {
            MusicianScreen(onNavigate = {destination -> navController.navigate(destination)}, isSelected = isSelectedBarItem(navController))
        }
        composable(route = AppScreens.AlbumScreen.route) {
            AlbumScreen(onNavigate = {destination -> navController.navigate(destination)}, isSelected = isSelectedBarItem(navController))
        }
        composable(route = AppScreens.CollectorScreen.route) {
            CollectorScreen(onNavigate = {destination -> navController.navigate(destination)}, isSelected = isSelectedBarItem(navController))
        }

    }
}

@Composable
fun isSelectedBarItem(navController: NavHostController): (String) -> Boolean {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return { rute -> navBackStackEntry?.destination?.route == rute }
}