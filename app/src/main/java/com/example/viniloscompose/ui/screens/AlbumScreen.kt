package com.example.viniloscompose.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.viniloscompose.R
import com.example.viniloscompose.model.dto.AlbumDto
import com.example.viniloscompose.model.repository.AlbumRepository
import com.example.viniloscompose.model.service.mocks.AlbumServiceMock
import com.example.viniloscompose.ui.navigation.AppScreens
import com.example.viniloscompose.ui.navigation.BottomNavigation
import com.example.viniloscompose.ui.navigation.isSelectedBarItem
import com.example.viniloscompose.ui.shared.ContentDescriptions
import com.example.viniloscompose.utils.cache.FixedCacheManager
import com.example.viniloscompose.utils.network.FixedNetworkValidator
import com.example.viniloscompose.viewModel.AlbumViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.logging.Logger

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AlbumScreen(
    onNavigate: (String) -> Unit,
    isSelected: (String) -> Boolean,
    albumRepository: AlbumRepository,
    viewModel: AlbumViewModel? = null
) {
    val albumViewModel = viewModel ?: remember {AlbumViewModel(albumRepository) }
    var query by remember { mutableStateOf("") }
    val state = albumViewModel.state
    Scaffold(
        bottomBar = {
            BottomNavigation(onNavigate, isSelected)
        },
        modifier = Modifier.semantics {
            contentDescription = ContentDescriptions.ALBUM_SCREEN.value
        }
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

        if (state.selectedAlbum != null) {
            val album = state.selectedAlbum
            AlbumDetailScreen(album, albumViewModel)
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 64.dp, bottom = 84.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleAlbum()
            Column() {
                SearchBarAlbum(onFilter = fun(newQuery: String) { query = newQuery })
                Spacer(modifier = Modifier.height(16.dp))
                val filteredAlbums = albumViewModel.getFilteredAlbums(query)
                BodyAlbumContent(filteredAlbums, albumViewModel)
            }

        }
    }
}

@Composable
fun AlbumDetailScreen(album: AlbumDto, albumViewModel: AlbumViewModel) {
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
                    albumViewModel.selectAlbum(null)
                },
                modifier = Modifier
                    .padding(16.dp)
                    .size(24.dp)
                    .align(Alignment.TopStart)
                    .semantics {
                        contentDescription = ContentDescriptions.ALBUM_CARD_BACK.value
                    }
            ){
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
                    text = album.releaseDate ?: "",
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
            Text(text = album.description,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray)
            Spacer(modifier = Modifier.height(18.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)

            ) {
                album.tracks?.forEach { track ->
                    Row(
                        modifier = Modifier
                            .semantics {
                                contentDescription =
                                    ContentDescriptions.ALBUM_CARD_PERFORMER_NAME.value
                            }
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 16.dp)
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
                            Row {
                                Text(
                                    text = track.name,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color.Black
                                )
                            }
                            Row {
                                Text(
                                    text = "${track.duration} min",
                                    style = MaterialTheme.typography.bodySmall,
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
                                .weight(1f)
                        )

                    }

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.Black)
                    )
                }
            }
        }
    }
}

@Composable
fun BodyAlbumContent(albums: List<AlbumDto>, albumViewModel: AlbumViewModel) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .semantics { contentDescription = ContentDescriptions.ALBUM_SCREEN_BODY.value },

        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        itemsIndexed(items = albums) { _, item ->
            CardAlbum(item, albumViewModel)
        }
    }
}

@Composable
fun TitleAlbum() {
    val formattedDate = SimpleDateFormat(
        "EEEE dd MMMM yyyy",
        Locale.getDefault()
    ).format(Calendar.getInstance().time)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.semantics {
            contentDescription = ContentDescriptions.ALBUM_SCREEN_TITLE.value
        }
    ) {
        Text(formattedDate)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(id = R.string.explora_albumes),
            modifier = Modifier.padding(
                top = 8.dp,
                bottom = 8.dp
            ),
            style = TextStyle(
                fontSize = 33.sp,
                color = Color(0xFF1D1B20),
                fontFamily = FontFamily.Default,
                textAlign = TextAlign.Left,
            ),
            lineHeight = 42.sp
        )
    }
}

@Composable
fun CardAlbum(item: AlbumDto, albumViewModel: AlbumViewModel) {

    Card(onClick = {
        //set the selected album
        albumViewModel.selectAlbum(item)
        //log the event
        Logger.getLogger("AlbumScreen").info("Album selected: ${item.name}")
    },
        modifier = Modifier
            .height(102.dp)
            .padding(vertical = 4.dp)
            .semantics { contentDescription = ContentDescriptions.ALBUM_CARD.value },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(86.dp)
                    .semantics {
                        contentDescription = ContentDescriptions.ALBUM_CARD_IMAGE.value
                    },
                model = item.cover,
                contentDescription = item.name
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.performers.joinToString { it.name },
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.semantics {
                        contentDescription = ContentDescriptions.ALBUM_CARD_PERFORMER_NAME.value
                    }
                )
            }

        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarAlbum(onFilter: (String) -> Unit) {
    var query by remember { mutableStateOf("") }
    SearchBar(
        query = query,
        onQueryChange = {
            query = it
            onFilter(query)
        },
        onSearch = {},
        active = false,
        onActiveChange = {},
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .semantics {
                contentDescription = ContentDescriptions.ALBUM_SCREEN_SEARCHBAR.value
            }, // Add horizontal padding to the search bar
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
        },
        placeholder = {
            Text(
                text = stringResource(id = R.string.buscar_album),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )
        },
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.mic_vector),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
        }
    ) {}
}

@Preview(showBackground = true)
@Composable
fun DefaultAlbumPreview() {
    val navController = rememberNavController()
    val cacheManager = FixedCacheManager()
    val networkValidator = FixedNetworkValidator(true)
    NavHost(navController, startDestination = AppScreens.AlbumScreen.route) {
        composable(AppScreens.AlbumScreen.route) {
            AlbumScreen(
                onNavigate = { dest -> navController.navigate(dest) },
                isSelected = isSelectedBarItem(navController),
                AlbumRepository(
                    cacheManager,
                    networkValidator,
                    AlbumServiceMock()
                )
            )
        }
    }
}
@Preview(showBackground = true)
@Preview(showBackground = true)
@Composable
fun SelectedAlbumPreview() {
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
            AlbumScreen(
                onNavigate = { dest -> navController.navigate(dest) },
                isSelected = isSelectedBarItem(navController),
                albumRepository,
                albumViewModel
            )
        }
    }
}