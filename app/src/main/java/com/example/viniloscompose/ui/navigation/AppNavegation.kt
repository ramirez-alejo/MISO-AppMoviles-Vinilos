package com.example.viniloscompose.ui.navigation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.viniloscompose.model.repository.AlbumRepository
import com.example.viniloscompose.model.repository.CollectorRepository
import com.example.viniloscompose.model.repository.MusicianRepository
import com.example.viniloscompose.model.service.VinilosService
import com.example.viniloscompose.ui.screens.AlbumScreen
import com.example.viniloscompose.ui.screens.CollectorScreen
import com.example.viniloscompose.ui.screens.InicioScreen
import com.example.viniloscompose.ui.screens.MusicianScreen
import com.example.viniloscompose.utils.cache.CacheManager
import com.example.viniloscompose.utils.network.NetworkValidator


@Composable
fun AppNavigation() {
    val application = LocalContext.current.applicationContext as Application
    val cacheManager = remember { CacheManager(application) }
    val networkValidator = NetworkValidator(application)
    val service = remember { VinilosService() }
    val albumRepository = remember { AlbumRepository(cacheManager, networkValidator, service) }
    val musicianRepository = remember { MusicianRepository(cacheManager, networkValidator, service) }
    val collectorRepository = remember { CollectorRepository(cacheManager, networkValidator, service) }

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreens.InicioScreen.route) {
        composable(route = AppScreens.InicioScreen.route) {
            InicioScreen(onNavigate = {destination -> navController.navigate(destination)})
        }
        composable(route = AppScreens.MusicianScreen.route) {
            MusicianScreen(
                onNavigate = {destination -> navController.navigate(destination)},
                isSelected = isSelectedBarItem(navController),
                musicianRepository = musicianRepository
            )
        }
        composable(route = AppScreens.AlbumScreen.route) {
            AlbumScreen(
                onNavigate = {destination -> navController.navigate(destination)},
                isSelected = isSelectedBarItem(navController),
                albumRepository = albumRepository
            )
        }
        composable(route = AppScreens.CollectorScreen.route) {
            CollectorScreen(
                onNavigate = {destination -> navController.navigate(destination)},
                isSelected = isSelectedBarItem(navController),
                collectorRepository = collectorRepository
            )
        }

    }
}

@Composable
fun isSelectedBarItem(navController: NavHostController): (String) -> Boolean {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return { rute -> navBackStackEntry?.destination?.route == rute }
}