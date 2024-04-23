package com.example.viniloscompose.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.viniloscompose.model.dto.MusicianDto
import com.example.viniloscompose.ui.navigation.AppScreens
import com.example.viniloscompose.viewModel.MusicianViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MusicianScreen(navController: NavController,musicianViewModel: MusicianViewModel = viewModel()){
    val state = musicianViewModel.state
    Scaffold {
        if(state.isLoading){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            )
            {
                CircularProgressIndicator()
            }
        }
        else{
                BodyMusicianContent(navController,musicianViewModel.response)
        }
    }
}

@Composable
fun BodyMusicianContent(navController:NavController,musicians:List<MusicianDto>){
    LazyColumn (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        itemsIndexed(items = musicians){index, item ->
            Card(
                modifier= Modifier
                    .padding(8.dp)
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Row {
                    AsyncImage(
                        model = item.image,
                        contentDescription = item.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                    )

                    Text(text = item.name)
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun DefaulMusiciatPreview(){
    val navController = rememberNavController()
    NavHost(navController, startDestination = AppScreens.MusicianScreen.route) {
        composable(AppScreens.MusicianScreen.route) {
            MusicianScreen(navController,viewModel())
        }
    }
}