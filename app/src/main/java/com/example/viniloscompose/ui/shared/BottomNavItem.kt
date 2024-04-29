package com.example.viniloscompose.ui.shared

import com.example.viniloscompose.R
import com.example.viniloscompose.ui.navigation.AppScreens

sealed class BottomNavItem(
    var title: String,
    var icon: Int,
    var rute: String
) {
    object Albums :
        BottomNavItem(
            "Álbumes",
            R.drawable.albunes_icon,
            AppScreens.AlbumScreen.route
        )

    object Musicians :
        BottomNavItem(
            "Artistas",
            R.drawable.artista_vector,
            AppScreens.MusicianScreen.route
        )

    object Collectors :
        BottomNavItem(
            "Coleccionistas",
            R.drawable.coleccionista_vector,
            AppScreens.CollectorScreen.route
        )
}