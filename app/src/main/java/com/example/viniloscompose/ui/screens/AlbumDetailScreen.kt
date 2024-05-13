package com.example.viniloscompose.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.viniloscompose.R
import com.example.viniloscompose.model.dto.AlbumDto
import com.example.viniloscompose.model.dto.TracksDto
import com.example.viniloscompose.model.repository.AlbumRepository
import com.example.viniloscompose.model.service.mocks.AlbumServiceMock
import com.example.viniloscompose.ui.navigation.AppScreens
import com.example.viniloscompose.ui.navigation.BottomNavigation
import com.example.viniloscompose.ui.navigation.isSelectedBarItem
import com.example.viniloscompose.ui.shared.ContentDescriptions
import com.example.viniloscompose.ui.shared.convertirFormatoFecha
import com.example.viniloscompose.utils.cache.FixedCacheManager
import com.example.viniloscompose.utils.network.FixedNetworkValidator
import com.example.viniloscompose.viewModel.AlbumViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AlbumDetailScreen(
    albumId: Int?,
    onNavigate: (String) -> Unit,
    isSelected: (String) -> Boolean,
    albumRepository: AlbumRepository,
    popBackStackAction: () -> Unit
) {
    val albumViewModel = remember { AlbumViewModel(albumRepository) }
    val album = albumViewModel.getAlbum(albumId!!)
    Scaffold(
        bottomBar = {
            BottomNavigation(onNavigate, isSelected)
        },
        modifier = Modifier.semantics {
            contentDescription = ContentDescriptions.ALBUM_SCREEN.value
        }
    ) {
        AlbumDetailScreenBody(album, popBackStackAction)
    }

}

@Composable
fun AlbumDetailScreenBody(album: AlbumDto, popBackStackAction: () -> Unit) {
    //Lets declare 4 colors and use them randomly in the gradient
    val colors = listOf(Color.Blue, Color.Red, Color.Green, Color.Yellow)
    val currentColor = colors.random()

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.4f)
                .background(Brush.verticalGradient(listOf(currentColor, Color.White)))
        ) {

            IconButton(
                onClick = {
                    popBackStackAction()
                },
                modifier = Modifier
                    .padding(16.dp)
                    .size(24.dp)
                    .align(Alignment.TopStart)
                    .semantics {
                        contentDescription = ContentDescriptions.ALBUM_CARD_BACK.value
                    }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = Color.White
                )
            }

            AsyncImage(
                modifier = Modifier
                    .size(220.dp)
                    .align(Alignment.BottomCenter)
                    .semantics {
                        contentDescription = ContentDescriptions.ALBUM_CARD_IMAGE.value
                    },
                model = album.cover,
                contentDescription = album.name
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.6f)
                .background(Color.White)
                .padding(16.dp)
        ) {
            Row {
                Text(
                    text = album.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = album.genre,
                    style = MaterialTheme.typography.bodyMedium,
                    color = currentColor,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
            Row(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .semantics {
                        contentDescription = ContentDescriptions.ALBUM_CARD_PERFORMER_NAME.value
                    }
            ) {

                Text(
                    text = album.performers.joinToString { it.name },
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
            }
            Row(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .semantics {
                        contentDescription = ContentDescriptions.ALBUM_CARD_PERFORMER_NAME.value
                    }
            ) {
                Text(
                    text = stringResource(id = R.string.fecha_de_lanzamiento),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = album.releaseDate?.let { convertirFormatoFecha(it) } ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
            }
            Row(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .semantics {
                        contentDescription = ContentDescriptions.ALBUM_CARD_PERFORMER_NAME.value
                    }
            ) {
                Text(
                    text = stringResource(id = R.string.sello_discografico),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = album.recordLabel,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.height(18.dp))
            Text(
                text = album.description,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(18.dp))
            if (album.tracks != null)
                AlbumTrackList(album.tracks!!)
        }
    }
}

@Composable
private fun AlbumTrackList(tracks: List<TracksDto>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 4.dp)
            .semantics { contentDescription = ContentDescriptions.ALBUM_SCREEN_BODY.value },

        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        itemsIndexed(items = tracks) { _, item ->
            CardTrack(item)
        }
    }
}

@Composable
private fun CardTrack(track: TracksDto) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 1.dp),
        shape = RectangleShape
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {

                Icon(
                    imageVector = Icons.Rounded.PlayArrow,
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { }
                )
                Spacer(modifier = Modifier.width(18.dp))
                Column {
                    Text(
                        text = track.name,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Clip,
                        modifier = Modifier
                            .widthIn(max = 250.dp),
                        color = Color.Black
                    )


                    Text(
                        text = "${track.duration} min",
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.Black
                    )
                }
            }
            Icon(
                imageVector = Icons.Rounded.Favorite,
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .clickable { }
            )
        }

    }
}


@Preview(showBackground = true)
@Composable
fun AlbumDetailScreenPreview() {
    val navController = rememberNavController()
    val cacheManager = FixedCacheManager()
    val networkValidator = FixedNetworkValidator(true)

    val albumRepository = AlbumRepository(
        cacheManager,
        networkValidator,
        AlbumServiceMock()
    )

    val albumViewModel = AlbumViewModel(albumRepository)
    albumViewModel.selectAlbum(albumRepository.getAlbums().first())
    NavHost(navController, startDestination = AppScreens.AlbumScreen.route) {
        composable(AppScreens.AlbumScreen.route) {
            AlbumDetailScreen(
                onNavigate = { dest -> navController.navigate(dest) },
                isSelected = isSelectedBarItem(navController),
                albumRepository = albumRepository,
                popBackStackAction = { navController.popBackStack() },
                albumId = 1

            )
        }
    }
}