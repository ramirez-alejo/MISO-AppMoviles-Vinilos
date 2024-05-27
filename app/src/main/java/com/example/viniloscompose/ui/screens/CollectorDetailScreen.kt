package com.example.viniloscompose.ui.screens

import android.annotation.SuppressLint
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.viniloscompose.R
import com.example.viniloscompose.model.dto.AlbumDto
import com.example.viniloscompose.model.dto.CollectorDto
import com.example.viniloscompose.model.dto.MusicianDto
import com.example.viniloscompose.model.repository.AlbumRepository
import com.example.viniloscompose.model.repository.CollectorRepository
import com.example.viniloscompose.model.repository.MusicianRepository
import com.example.viniloscompose.ui.navigation.BottomNavigation
import com.example.viniloscompose.ui.shared.ContentDescriptions
import com.example.viniloscompose.ui.shared.convertirFormatoFecha
import com.example.viniloscompose.viewModel.CollectorDetailViewModel
import com.example.viniloscompose.viewModel.state.CollectorDetailState


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CollectorDetailScreen(
    collectorId: Int?,
    collectorRepository: CollectorRepository,
    albumRepository: AlbumRepository,
    musicianRepository: MusicianRepository,
    onNavigate: (String) -> Unit,
    isSelected: (String) -> Boolean,
    popBackStackAction: () -> Unit,
    onAlbumClick: (Int) -> Unit,
    onMusicianClick: (Int) -> Unit
) {
    val collectorDetailViewModel = remember {
        CollectorDetailViewModel(
            collectorRepository,
            albumRepository,
            musicianRepository,
            collectorId!!
        )
    }
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val state = collectorDetailViewModel.state


    Scaffold(
        bottomBar = {
            BottomNavigation(onNavigate, isSelected)
        },
        topBar = { TopBar(popBackStackAction) },
        modifier = Modifier.semantics {
            contentDescription = ContentDescriptions.COLLECTOR_DETAIL_SCREEN.value
        }
    ) {
        when (state) {
            is CollectorDetailState.Loading -> {
                CollectorScreenLoanding()
            }

            is CollectorDetailState.Success -> {
                CollectorDetailBody(
                    state.collector,
                    state.albums,
                    state.favoriteMusicians,
                    selectedTabIndex,
                    { index ->
                        selectedTabIndex = index
                    },
                    onAlbumClick,
                    onMusicianClick)
            }

            is CollectorDetailState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = R.string.error.toString(), style = TextStyle(
                            fontSize = 20.sp,
                            color = contentColorFor(Color.White),
                            fontFamily = FontFamily.Default,
                            textAlign = TextAlign.Center
                        )
                    )
                }

            }
        }

    }
}

@Composable
private fun CollectorDetailBody(
    collector: CollectorDto,
    albums: List<AlbumDto>,
    favoriteMusicians: List<MusicianDto>,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    onAlbumClick: (Int) -> Unit,
    onMusicianClick: (Int) -> Unit
) {
    Column {
        Spacer(modifier = Modifier.size(40.dp))
        CollectorInformationContent(collector)
        Spacer(modifier = Modifier.height(16.dp))
        CollectorTabs(
            selectedTabIndex,
            onTabSelected,
            albums,
            favoriteMusicians,
            onAlbumClick,
            onMusicianClick
        )
    }

}


@Composable
private fun CollectorInformationContent(collector: CollectorDto) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .semantics {
                contentDescription = ContentDescriptions.COLLECTOR_DETAIL_INFORMATION.value
            }
    ) {
        AsyncImage(
            model = R.drawable.collector_icon,
            contentDescription = collector.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(92.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.size(4.dp))
        Column {
            Text(
                text = collector.name,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = Color(0xFF1D1B20),
                modifier = Modifier.padding(top = 4.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,


                ) {
                Icon(
                    imageVector = Icons.Outlined.Phone,
                    contentDescription = null,
                    tint = Color(0x75000000),
                    modifier = Modifier.size(10.5.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = collector.telephone,
                    style = MaterialTheme.typography.labelSmall
                )
                Spacer(modifier = Modifier.width(5.dp))
                Icon(
                    imageVector = Icons.Outlined.MailOutline,
                    contentDescription = null,
                    tint = Color(0x75000000),
                    modifier = Modifier.size(10.5.dp)
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = collector.email,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}


@Composable
private fun CollectorTabs(
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    albums: List<AlbumDto>,
    favoriteMusicians: List<MusicianDto>,
    onAlbumClick: (Int) -> Unit,
    onMusicianClick: (Int) -> Unit
) {
    // Barra de Pestañas
    val tabs = listOf("Artistas", "Álbumes")

    TabRow(
        selectedTabIndex = selectedTabIndex
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = { onTabSelected(index) },
                text = { Text(text = title) },
                modifier = Modifier.semantics { contentDescription = "CollectorDetailTab$title" }
            )
        }
    }

    // Contenido de la Pestaña Seleccionada
    when (selectedTabIndex) {
        0 -> FavoriteMusiciansTabContent(favoriteMusicians, onMusicianClick)
        1 -> AlbumsTabContent(albums, onAlbumClick)

    }
}

@Composable
fun AlbumsTabContent(albums: List<AlbumDto>, onAlbumClick: (Int) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 4.dp)
            .semantics { contentDescription = ContentDescriptions.ALBUM_SCREEN_BODY.value },

        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        itemsIndexed(items = albums) { _, item ->
            CardCollectorAlbumDetail(item, onAlbumClick)
        }
    }

}

@Composable
private fun CardCollectorAlbumDetail(item: AlbumDto, onAlbumClick: (Int) -> Unit) {
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


@Composable
fun FavoriteMusiciansTabContent(
    favoriteMusicians: List<MusicianDto>,
    onMusicianClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .semantics { contentDescription = ContentDescriptions.MUSICIANS_SCREEN_BODY.value },
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        itemsIndexed(items = favoriteMusicians) { _, item ->
            CardColletorMusician(item, onMusicianClick)
        }
    }
}

@Composable
private fun CardColletorMusician(item: MusicianDto, onCardClick: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 4.dp)
            .clickable {
                onCardClick(item.id)
            }
            .semantics { contentDescription = ContentDescriptions.MUSICIAN_CARD.value }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AsyncImage(
                    model = item.image,
                    contentDescription = item.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(60.dp)
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
                        color = Color.Black

                    )
                    Text(
                        text = stringResource(id = R.string.fecha_nacimiento) + convertirFormatoFecha(
                            item.birthDate
                        ),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF1D1B20)
                    )
                    Text(
                        text = item.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF1D1B20)
                    )
                }
            }
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        onCardClick(item.id)
                    }
            )

        }
    }

}

@Composable
private fun CollectorScreenLoanding() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    )
    {
        CircularProgressIndicator()
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
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                tint = Color.Black
            )
        }
    }
}