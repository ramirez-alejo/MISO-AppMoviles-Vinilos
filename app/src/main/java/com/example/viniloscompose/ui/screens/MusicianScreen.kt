package com.example.viniloscompose.ui.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.example.viniloscompose.model.dto.MusicianDto
import com.example.viniloscompose.ui.navigation.AppScreens
import com.example.viniloscompose.ui.navigation.BottomNavigation
import com.example.viniloscompose.viewModel.MusicianViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MusicianScreen(
    navController: NavController,
    musicianViewModel: MusicianViewModel = viewModel()
) {
    val state = musicianViewModel.state
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
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SearchBarMusician(musicianViewModel.response)
                TitleMusician()
                BodyMusicianContent(musicianViewModel.response)
            }
        }
    }
}


@Composable
fun BodyMusicianContent(musicians: List<MusicianDto>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        itemsIndexed(items = musicians) { _, item ->
            CardMusician(item)
        }
    }
}

@Composable
fun TitleMusician() {
    Text(
        text = "Artistas del momento",
        modifier = Modifier
            .width(180.dp)
            .height(43.dp),
        style = TextStyle(
            fontSize = 18.sp,
            color = Color(0xFF1D1B20),
            fontFamily = FontFamily.Default,
            textAlign = TextAlign.Left,
        ),
        lineHeight = 42.sp
    )
}

@Composable
fun CardMusician(item: MusicianDto) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .weight(0.2f)
                    .height(80.dp),
                contentAlignment = Alignment.Center
            )
            {
                AsyncImage(
                    model = item.image,
                    contentDescription = item.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                )
            }

            Box(
                modifier = Modifier
                    .weight(0.6f)
                    .height(80.dp),
                contentAlignment = Alignment.CenterStart
            )
            {
                Column {
                    Text(text = item.name)
                    Text(text = "Artista")
                }
            }

            Box(
                modifier = Modifier
                    .weight(0.2f)
                    .height(80.dp),
                contentAlignment = Alignment.Center
            )
            {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable {  }
                )
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarMusician(musicians: List<MusicianDto>) {
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
        val filteredMusicians =
            musicians.filter { it.name.contains(query, true) }
        BodyMusicianContent(filteredMusicians)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaulMusiciatPreview() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = AppScreens.MusicianScreen.route) {
        composable(AppScreens.MusicianScreen.route) {
            MusicianScreen(navController, viewModel())
        }
    }
}