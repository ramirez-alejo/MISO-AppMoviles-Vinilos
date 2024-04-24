package com.example.viniloscompose.view.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import com.example.viniloscompose.viewmodel.album.AlbumViewModel
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.viniloscompose.model.album.Album
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text

@Composable
fun AlbumScreen(navController: NavController, albumViewModel: AlbumViewModel) {
    val albums by albumViewModel.albums.observeAsState()
    val isLoading by albumViewModel.refreshing.observeAsState()

    if (isLoading != false) {
        CircularProgressIndicator()
    } else {
        Text(text = "Albums")

        albums?.let {
            LazyColumn {
                items(it) { album ->
                    AlbumCard(album = album, navController = navController)
                }
            }

        }
    }
}

@Composable
fun AlbumCard(album: Album, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                navController.navigate("album_detail_screen/${album.id}")
            }
    ) {
        Column {
            Text(text = album.name)
            Text(text = album.genre)
            Text(text = album.releaseDate)
            //AsyncImage(url = album.cover, contentDescription = album.name)
        }
    }
}