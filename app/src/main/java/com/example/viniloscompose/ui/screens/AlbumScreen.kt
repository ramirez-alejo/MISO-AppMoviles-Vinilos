package com.example.viniloscompose.ui.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.viniloscompose.R
import com.example.viniloscompose.model.dto.AlbumDto
import com.example.viniloscompose.ui.navigation.AppScreens
import com.example.viniloscompose.ui.navigation.BottomNavigation
import com.example.viniloscompose.ui.navigation.isSelectedBarItem
import com.example.viniloscompose.viewModel.AlbumViewModel
import java.text.SimpleDateFormat
import java.util.*
import com.example.viniloscompose.ui.shared.ContentDescriptions

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AlbumScreen(
    onNavigate: (String) -> Unit,
    isSelected: (String) -> Boolean,
    albumViewModel: AlbumViewModel = viewModel()
) {
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
        } else {
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
        Text(
            text = "Explora Álbumes",
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp,
                top = 8.dp,
                bottom = 8.dp
            ), // Add padding to the title
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
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth()
            .height(90.dp)
            .clip(RoundedCornerShape(8.dp))
            .semantics { contentDescription = ContentDescriptions.ALBUM_CARD.value }, // Clip card corners to match Figma design
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = item.cover,
                    contentDescription = item.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.semantics {
                        contentDescription = ContentDescriptions.ALBUM_CARD_IMAGE.value
                    }
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Column for album details
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.performers.joinToString { it.name },
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray,
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
fun SearchBarAlbum(albums: List<AlbumDto>) {
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val ctx = LocalContext.current
    SearchBar(
        query = query,
        onQueryChange = { query = it },
        onSearch = {
            Toast.makeText(ctx, "buscando", Toast.LENGTH_LONG).show()
            active = false
        },
        active = active,
        onActiveChange = {
            active = it
            if (!active)
                query = ""
        },
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
        trailingIcon = {
            Icon(
                painter = painterResource(R.drawable.mic_vector),
                contentDescription = null,
                modifier = Modifier.size(17.dp)
            )
        }
    ) {
        val filteredAlbums =
            albums.filter { it.name.contains(query, true) }
        BodyAlbumContent(filteredAlbums)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultAlbumPreview() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = AppScreens.AlbumScreen.route) {
        composable(AppScreens.AlbumScreen.route) {
            AlbumScreen(
                onNavigate = { dest -> navController.navigate(dest) },
                isSelected = isSelectedBarItem(navController), viewModel()
            )
        }
    }
}