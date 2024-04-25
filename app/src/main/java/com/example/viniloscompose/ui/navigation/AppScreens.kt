package com.example.viniloscompose.ui.navigation

sealed class AppScreens (val route: String) {
    data object InicioScreen: AppScreens("login_screen")
    data object MusicianScreen: AppScreens("musicians_screen")

    data object  AlbumScreen: AppScreens("albums_screen")

    data object  CollectorScreen: AppScreens("collectors_screen")


}