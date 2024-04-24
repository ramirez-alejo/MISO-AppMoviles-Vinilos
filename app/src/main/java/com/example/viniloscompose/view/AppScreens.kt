package com.example.viniloscompose.view

sealed class AppScreens (val route: String) {
    object InicioScreen: AppScreens("login_screen")
    object MusicianScreen: AppScreens("musicians_screen")
    object AlbumScreen: AppScreens("albums_screen")

}