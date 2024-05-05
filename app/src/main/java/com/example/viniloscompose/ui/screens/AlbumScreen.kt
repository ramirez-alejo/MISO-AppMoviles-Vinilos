package com.example.viniloscompose.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.viniloscompose.R
import com.example.viniloscompose.model.dto.AlbumDto
import com.example.viniloscompose.model.repository.AlbumRepository
import com.example.viniloscompose.model.service.mocks.AlbumServiceMock
import com.example.viniloscompose.model.repository.AlbumRepository
import com.example.viniloscompose.model.service.VinilosService
import com.example.viniloscompose.ui.navigation.AppScreens
import com.example.viniloscompose.ui.navigation.BottomNavigation
import com.example.viniloscompose.ui.navigation.isSelectedBarItem
import com.example.viniloscompose.ui.shared.ContentDescriptions
import com.example.viniloscompose.ui.shared.ContentDescriptions
import com.example.viniloscompose.utils.cache.FixedCacheManager
import com.example.viniloscompose.utils.network.FixedNetworkValidator
import com.example.viniloscompose.viewModel.AlbumViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AlbumScreen(
    onNavigate: (String) -> Unit,
    isSelected: (String) -> Boolean,
    albumViewModel: AlbumViewModel
) {
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
        } else if (state.error != null) {
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
                    BodyAlbumContent(filteredAlbums)
                }

            }
        }
        else {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TitleAlbum()
                SearchBarAlbum(albumViewModel.response)
                Spacer(modifier = Modifier.height(16.dp))
                BodyAlbumContent(albumViewModel.response)
            }
        }
    }
}


@Composable
fun BodyAlbumContent(albums: List<AlbumDto>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .semantics { contentDescription = ContentDescriptions.ALBUM_SCREEN_BODY.value },

        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        itemsIndexed(items = albums) { _, item ->
            CardAlbum(item)
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
fun CardAlbum(item: AlbumDto) {
        Card(
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
                    modifier = Modifier.size(86.dp)
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
            .background(
                color = Color.White
            )
            .semantics { contentDescription = ContentDescriptions.ALBUM_SCREEN_SEARCHBAR.value }, // Add horizontal padding to the search bar
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
    ){}
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
                AlbumViewModel(
                    AlbumRepository(
                        cacheManager,
                        networkValidator,
                        AlbumServiceMock()
                    )
                )
            )
        }
    }
}