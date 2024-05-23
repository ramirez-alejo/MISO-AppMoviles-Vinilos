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
    ALBUM_SCREEN_SEARCHBAR("AlbumSearchBar"),
    ALBUM_SCREEN_TITLE("AlbumTitle"),
    ALBUM_SCREEN_BODY("AlbumScreenBody"),
    ALBUM_DETAIL_SCREEN("AlbumDetailScreen"),
    ALBUM_DETAIL_BACK("AlbumDetailBack"),
    ALBUM_DETAIL_TITLE("AlbumDetailTitle"),
    ALBUM_DETAIL_GENRE("AlbumDetailGenre"),
    ALBUM_DETAIL_DATE("AlbumDetailDate"),
    ALBUM_DETAIL_LABEL("AlbumDetailLabel"),
    ALBUM_DETAIL_TRACK_LIST("AlbumDetailTrackList"),
    ALBUM_DETAIL_FORM_TITLE("AlbumDetailTrackFormTitle"),
    ALBUM_DETAIL_TRACK_FORM_NAME("AlbumDetailTrackFormName"),
    ALBUM_DETAIL_TRACK_FORM_DURATION("AlbumDetailTrackDuration"),
    ALBUM_DETAIL_TRACK_FORM_CANCEL("AlbumDetailTrackFormCancel"),
    ALBUM_DETAIL_TRACK_FORM_CREATE("AlbumDetailTrackFormCreate"),
    ALBUM_DETAIL_CARD_TRACK("AlbumDetailTrackFormCreate"),
    ALBUM_DETAIL_TRACK_CREATE("AlbumDetailTrackCreate"),
    ALBUM_DETAIL_BODY("AlbumDetailScreenBody"),
    ALBUM_CARD("AlbumCard"),
    ALBUM_CARD_IMAGE("AlbumCardImage"),
    ALBUM_CARD_PERFORMER_NAME("AlbumCardPerformerName"),
    COLLECTOR_SCREEN("CollectorScreen"),
    COLLECTOR_SCREEN_SEARCHBAR("CollectorScreenSearchBar"),
    COLLECTOR_SCREEN_TITLE("CollectorScreenTitle"),
    COLLECTOR_SCREEN_BODY("CollectorScreenBody"),
    COLLECTOR_CARD("CollectorCard"),
    COLLECTOR_CARD_IMAGE("CollectorCardImage"),
    COLLECTOR_CARD_NAME("CollectorCardName"),
    COLLECTOR_DETAIL_SCREEN("CollectorDetailScreen"),
    COLLECTOR_DETAIL_INFORMATION("CollectorDetailInformation"),
    COLLECTOR_DETAIL_ALBUMS_TAB("CollectorDetailTabÁlbumes"),
    COLLECTOR_DETAIL_MUSICIANS_TAB("CollectorDetailTabArtistas"),
    COLLECTOR_VIEW_DETAIL("ViewCollectorDetails"),
    INICIO_SCREEN("InicioScreen"),
    MUSICIAN_SCREEN("MusicianScreen"),
    MUSICIANS_SCREEN_SEARCHBAR("MusicianSearchBar"),
    MUSICIANS_SCREEN_TITLE("MusicianTitle"),
    MUSICIANS_SCREEN_BODY("MusicianScreenBody"),
    MUSICIAN_CARD("MusicianCard"),
    MUSICIAN_CARD_IMAGE("MusicianCardImage"),
    MUSICIAN_DETAIL_RETURN_ACCION("ReturnToMusicianScreen"),
    MUSICIAN_VIEW_DETAIL("ViewMusicianDetails")
}