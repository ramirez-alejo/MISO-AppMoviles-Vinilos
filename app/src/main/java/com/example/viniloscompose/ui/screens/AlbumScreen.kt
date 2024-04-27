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
import com.example.viniloscompose.viewModel.AlbumViewModel
import com.example.viniloscompose.viewModel.MockAlbumViewModel
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AlbumScreen(
    navController: NavController,
    albumViewModel: AlbumViewModel = viewModel()
) {
    var query by remember { mutableStateOf("") }
    val state = albumViewModel.state
    Scaffold(
        bottomBar = {
            BottomNavigation(navController)
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
    }
}


@Composable
fun BodyAlbumContent(albums: List<AlbumDto>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        itemsIndexed(items = albums) { _, item ->
            CardAlbum(item)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun TitleAlbum() {
    val formattedDate = SimpleDateFormat("EEEE dd MMMM yyyy", Locale.getDefault()).format(Calendar.getInstance().time)
    Column (
        horizontalAlignment = Alignment.CenterHorizontally
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
                .height(102.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp)
            ) {
                AsyncImage(
                    modifier = Modifier.size(86.dp),
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
                        style = MaterialTheme.typography.titleSmall
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

            ),
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
    NavHost(navController, startDestination = AppScreens.AlbumScreen.route) {
        composable(AppScreens.AlbumScreen.route) {
            val vm = viewModel(MockAlbumViewModel::class.java)
            AlbumScreen(navController, vm)
        }
    }
}