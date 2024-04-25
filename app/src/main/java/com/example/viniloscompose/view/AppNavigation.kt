package com.example.viniloscompose.view

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.viniloscompose.view.screens.InicioScreen
import com.example.viniloscompose.view.screens.AlbumScreen
import com.example.viniloscompose.viewmodel.album.AlbumViewModel
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    val albumViewModel : AlbumViewModel = hiltViewModel()
    NavHost(navController = navController, startDestination = AppScreens.InicioScreen.route ){
        composable(route = AppScreens.InicioScreen.route ){
            InicioScreen(navController)
        }
        /*composable(route = AppScreens.MusicianScreen.route){
            MusicianScreen(navController)
        }*/

        composable(route = AppScreens.AlbumScreen.route){
            AlbumScreen(navController,albumViewModel)
        }

    }
}