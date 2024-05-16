package com.example.viniloscompose.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.example.viniloscompose.viewModel.AlbumSongFormViewModel
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
    val state = albumViewModel.state
    //Lets declare 4 colors and use them randomly in the gradient
    val colors = listOf(Color.Blue, Color.Red, Color.LightGray, Color.Yellow)
    val currentColor = colors.random()
    Scaffold(
        bottomBar = {
            BottomNavigation(onNavigate, isSelected)
        },
        modifier = Modifier
            .semantics {
                contentDescription = ContentDescriptions.ALBUM_SCREEN.value
            },

        ) {
        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            )
            {
                CircularProgressIndicator()
            }
            return@Scaffold
        }

        if (state.error != null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = R.string.error.toString(),
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = contentColorFor(Color.White),
                        fontFamily = FontFamily.Default,
                        textAlign = TextAlign.Center
                    )
                )
            }
            return@Scaffold
        }
        AlbumDetailScreenBody(
            albumViewModel.getAlbum(albumId!!),
            popBackStackAction,
            it,
            currentColor,
            albumRepository
        )
    }

}

@Composable
fun AlbumDetailScreenBody(
    album: AlbumDto,
    paddingpopBackStackAction: () -> Unit,
    paddingValues: PaddingValues,
    currentColor: Color,
    albumRepository: AlbumRepository
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Box {
                    Row(
                        modifier = Modifier
                            .background(
                                Brush.verticalGradient(
                                    listOf(currentColor, MaterialTheme.colorScheme.background),
                                    0f
                                )
                            )
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 80.dp)
                                .size(250.dp)
                                .semantics {
                                    contentDescription = ContentDescriptions.ALBUM_CARD_IMAGE.value
                                },
                            model = album.cover,
                            contentDescription = album.name
                        )
                    }
                    IconButton(
                        onClick = {
                            paddingpopBackStackAction()
                        },
                        modifier = Modifier
                            .padding(top = 32.dp, start = 16.dp)
                            .size(24.dp)
                            .semantics {
                                contentDescription = ContentDescriptions.ALBUM_CARD_BACK.value
                            }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.inverseSurface
                        )
                    }
                }
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 11.dp)) {
                    Row {
                        Text(
                            text = album.name,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = album.genre,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.align(Alignment.CenterVertically),
                            color = currentColor
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .semantics {
                                contentDescription =
                                    ContentDescriptions.ALBUM_CARD_PERFORMER_NAME.value
                            }
                    ) {

                        Text(
                            text = album.performers!!.joinToString { it.name },
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .semantics {
                                contentDescription =
                                    ContentDescriptions.ALBUM_CARD_PERFORMER_NAME.value
                            }
                    ) {
                        Text(
                            text = stringResource(id = R.string.fecha_de_lanzamiento),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = album.releaseDate?.let { convertirFormatoFecha(it) } ?: "",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .semantics {
                                contentDescription =
                                    ContentDescriptions.ALBUM_CARD_PERFORMER_NAME.value
                            }
                    ) {
                        Text(
                            text = stringResource(id = R.string.sello_discografico),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = album.recordLabel,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Spacer(modifier = Modifier.height(18.dp))
                    Text(
                        text = album.description,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Thin
                    )
                    Spacer(modifier = Modifier.height(18.dp))

                    AlbumTrackList(album, album.tracks, albumRepository)
                }
            }
        }
    }
}

@Composable
private fun AlbumTrackList(
    album: AlbumDto,
    tracks: List<TracksDto>?,
    albumRepository: AlbumRepository
) {
    var openForm by remember {
        mutableStateOf(false)
    }

    if (!openForm) {
        if (tracks != null) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 4.dp)
                    .semantics { contentDescription = ContentDescriptions.ALBUM_SCREEN_BODY.value }
                    .heightIn(0.dp, 500.dp),

                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                itemsIndexed(items = tracks) { _, item ->
                    CardTrack(item)
                }
            }
        }
        AlbumCreateButton(onClick = { openForm = true })
    } else {
        val viewModel = AlbumSongFormViewModel(albumRepository)
        AlbumSongForm(album, onCancel = { openForm = false }, viewModel = viewModel)
    }

}

@Composable
private fun AlbumSongForm(
    album: AlbumDto,
    onCancel: () -> Unit,
    viewModel: AlbumSongFormViewModel
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row {
                Text(
                    stringResource(id = R.string.agregar_album_cancion_form_title),
                    style = MaterialTheme.typography.labelLarge
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                TextField(
                    value = viewModel.songName,
                    onValueChange = { viewModel.setName(it) },
                    label = { Text(stringResource(id = R.string.agregar_album_cancion_nombre)) },
                    maxLines = 2,
                    textStyle = TextStyle(
                        color = MaterialTheme.colorScheme.inverseSurface,
                        fontWeight = FontWeight.SemiBold
                    ),
                    modifier = Modifier
                        .weight(0.6f)
                        .fillMaxWidth(),
                    isError = !viewModel.stateOfName(),
                    supportingText = { Text(stringResource(id = R.string.agregar_album_cancion_nombre_ayuda)) }
                )
                Spacer(modifier = Modifier.width(16.dp))
                TextField(
                    value = viewModel.songDuration,
                    onValueChange = { viewModel.setDuration(it) },
                    label = { Text(stringResource(id = R.string.agregar_album_cancion_duracion)) },
                    maxLines = 2,
                    textStyle = TextStyle(
                        color = MaterialTheme.colorScheme.inverseSurface,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .weight(0.4f)
                        .fillMaxWidth(),
                    isError = !viewModel.stateOfDuration(),
                    supportingText = { Text(stringResource(id = R.string.agregar_album_cancion_duracion_ayuda)) }
                )
            }
            Row(
                modifier = Modifier
                    .align(alignment = Alignment.End)
                    .padding(top = 16.dp)
            ) {
                TextButton(onClick = onCancel) {
                    Text(stringResource(id = R.string.agregar_album_cancion_cancelar))
                }
                Button(
                    onClick = { viewModel.addTrackToAlbum(album.id, onCancel) },
                    enabled = viewModel.stateOfName() && viewModel.stateOfDuration()
                ) {
                    Text(stringResource(id = R.string.agregar_album_cancion_guardar))
                }
            }
        }
    }
}

@Composable
private fun AlbumCreateButton(
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = Modifier.fillMaxWidth()
    ) {
        TextButton(
            onClick = onClick,
            modifier = Modifier
                .semantics {
                    contentDescription = ContentDescriptions.ALBUM_CARD_BACK.value
                }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Sharp.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = stringResource(id = R.string.agregar_album),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
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
                IconButton(
                    onClick = { /*TODO*/ },
                ) {
                    Icon(
                        imageVector = Icons.Rounded.PlayArrow,
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp)
                            .clickable { }
                            .border(1.dp, MaterialTheme.colorScheme.inverseSurface, CircleShape)
                    )
                }
                Spacer(modifier = Modifier.width(18.dp))
                Column {
                    Text(
                        text = track.name,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Clip,
                        modifier = Modifier
                            .widthIn(max = 250.dp)
                    )
                    Text(
                        text = "${track.duration} min",
                        style = MaterialTheme.typography.bodySmall
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