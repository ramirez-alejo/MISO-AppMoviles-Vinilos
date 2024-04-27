package com.example.viniloscompose.ui.shared

enum class LoginType(
    val value: String
) {
    COLLECTOR("collector"),
    VISITOR("visitor"),
}

enum class BottomNavigationSection(
    val value: String
) {
    ALBUM(BottomNavItem.Albums.title),
    MUSICIAN(BottomNavItem.Musicians.title),
    COLLECTOR(BottomNavItem.Collectors.title)
}

enum class ContentDescriptions(val value: String) {
    LOGIN_COLLECTOR("Collecionista"),
    LOGIN_VISITOR("Visitor"),
    BOTTOM_NAVIGATION("Bottom Navigation"),
    ALBUM_SCREEN("AlbumScreen"),
    COLLECTOR_SCREEN("CollectorScreen"),
    INICIO_SCREEN("InicioScreen"),
    MUSICIAN_SCREEN("MusicianScreen"),
    MUSICIANS_SCREEN_SEARCHBAR("MusicianSearchBar"),
    MUSICIANS_SCREEN_TITLE("MusicianTitle"),
    MUSICIANS_SCREEN_BODY("MusicianScreenBody"),
    MUSICIAN_CARD("MusicianCard"),
    MUSICIAN_CARD_IMAGE("MusicianCardImage")
}