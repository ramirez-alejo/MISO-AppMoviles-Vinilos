package com.example.viniloscompose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.viniloscompose.ui.screens.InicioScreen
import com.example.viniloscompose.ui.screens.MusicianScreen
import com.example.viniloscompose.viewModel.MusicianViewModel
import androidx.activity.viewModels

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.InicioScreen.route ){
        composable(route = AppScreens.InicioScreen.route ){
            InicioScreen(navController)
        }
        composable(route = AppScreens.MusicianScreen.route){
            MusicianScreen(navController)
        }

    }
}