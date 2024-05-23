package com.example.viniloscompose.ui.screens

import android.annotation.SuppressLint
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.viniloscompose.R
import com.example.viniloscompose.model.dto.MusicianAlbumDetailDto
import com.example.viniloscompose.model.dto.MusicianDto
import com.example.viniloscompose.model.repository.MusicianRepository
import com.example.viniloscompose.model.service.mocks.MusicianServiceMock
import com.example.viniloscompose.ui.navigation.AppScreens
import com.example.viniloscompose.ui.navigation.BottomNavigation
import com.example.viniloscompose.ui.shared.ContentDescriptions
import com.example.viniloscompose.ui.shared.convertirFormatoFecha
import com.example.viniloscompose.utils.cache.FixedCacheManager
import com.example.viniloscompose.utils.network.FixedNetworkValidator
import com.example.viniloscompose.viewModel.MusicianViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MusicianDetailScreen(
    musicianId: Int?,
    musicianRepository: MusicianRepository,
    onNavigate: (String) -> Unit,
    isSelected: (String) -> Boolean,
    popBackStackAction: () -> Unit,
    onAlbumClick: (Int) -> Unit
) {
    val musicianViewModel = remember { MusicianViewModel(musicianRepository) }
    val item = musicianViewModel.getMusician(musicianId!!)

    Scaffold(
        bottomBar = {
            BottomNavigation(onNavigate, isSelected)
        },
        topBar = { TopBar(popBackStackAction) },
        modifier = Modifier.semantics {
            contentDescription = ContentDescriptions.MUSICIAN_SCREEN.value
        }
    ) {
        BodyMusicianDetailContent(item, onAlbumClick)
    }

}

@Composable
private fun TopBar(popBackStackAction: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        IconButton(
            onClick = {
                popBackStackAction()
            },
            modifier = Modifier
                .padding(8.dp)
                .size(28.dp)
                .align(Alignment.TopStart)
                .semantics {
                    contentDescription = ContentDescriptions.MUSICIAN_DETAIL_RETURN_ACCION.value
                }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                tint = Color.Black
            )
        }
    }
}

@Composable
private fun BodyMusicianDetailContent(item: MusicianDto, onAlbumClick: (Int) -> Unit) {
    Column {
        Spacer(modifier = Modifier.size(40.dp))
        MusicianInformationContent(item)
        Spacer(modifier = Modifier.height(16.dp))
        MusicianAlbumsContent(item.albums, onAlbumClick)
    }
}

@Composable
private fun MusicianInformationContent(item: MusicianDto) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp),
    ) {
        AsyncImage(
            model = item.image,
            contentDescription = item.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(92.dp)
                .clip(CircleShape)
                .semantics {
                    contentDescription = ContentDescriptions.MUSICIAN_CARD_IMAGE.value
                }
        )
        Spacer(modifier = Modifier.size(4.dp))
        Column {
            Text(
                text = item.name,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = Color(0xFF1D1B20),
                modifier = Modifier.padding(top = 4.dp)
            )
            Text(
                text = stringResource(id = R.string.fecha_nacimiento) + convertirFormatoFecha(item.birthDate),
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
            )
            Text(
                text = item.description,
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}

@Composable
private fun MusicianAlbumsContent(
    albums: List<MusicianAlbumDetailDto>,
    onAlbumClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 84.dp, start = 16.dp),
    ) {
        TitleMusicianAlbums()
        Spacer(modifier = Modifier.height(12.dp))
        MusicianAlbumsList(albums, onAlbumClick)
    }
}

@Composable
private fun MusicianAlbumsList(albums: List<MusicianAlbumDetailDto>, onAlbumClick: (Int) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 4.dp)
            .semantics { contentDescription = ContentDescriptions.ALBUM_SCREEN_BODY.value },

        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        itemsIndexed(items = albums) { _, item ->
            CardAlbumDetail(item, onAlbumClick)
        }
    }
}

@Composable
private fun TitleMusicianAlbums() {
    Text(
        text = stringResource(id = R.string.artista_grandes_exitos),
        style = TextStyle(
            fontSize = 18.sp,
            color = Color(0xFF1D1B20),
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight(700),
            letterSpacing = 0.5.sp
        ),
        modifier = Modifier
            .semantics {
                contentDescription = ContentDescriptions.MUSICIANS_SCREEN_TITLE.value
            },
        lineHeight = 42.sp
    )
}


@Composable
private fun CardAlbumDetail(item: MusicianAlbumDetailDto, onAlbumClick: (Int) -> Unit) {
    Card(
        onClick = { onAlbumClick(item.id) },
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
                    color = Color.Black,
                    modifier = Modifier.semantics {
                        contentDescription =
                            ContentDescriptions.ALBUM_DETAIL_TITLE.value + item.name
                    }
                )
                Spacer(modifier = Modifier.height(4.dp))
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultMusicianDetailPreview() {
    val navController = rememberNavController()
    val cacheManager = FixedCacheManager()
    val networkValidator = FixedNetworkValidator(true)
    NavHost(navController, startDestination = AppScreens.MusicianDetailScreen.route) {
        composable(route = AppScreens.MusicianDetailScreen.route) {
            MusicianDetailScreen(
                musicianId = 1,
                musicianRepository = MusicianRepository(
                    cacheManager,
                    networkValidator,
                    MusicianServiceMock()
                ),
                onNavigate = { destination -> navController.navigate(destination) },
                isSelected = { rute -> AppScreens.MusicianScreen.route == rute },
                popBackStackAction = { navController.popBackStack() },
                onAlbumClick = { id -> navController.navigate(AppScreens.AlbumDetailScreen.route + "/$id") }
            )

        }
    }

}


