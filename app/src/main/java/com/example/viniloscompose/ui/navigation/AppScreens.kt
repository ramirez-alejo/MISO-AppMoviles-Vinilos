package com.example.viniloscompose.ui.navigation

sealed class AppScreens (val route: String) {
    object InicioScreen: AppScreens("login_screen")
    object MusicianScreen: AppScreens("musicians_screen")

}