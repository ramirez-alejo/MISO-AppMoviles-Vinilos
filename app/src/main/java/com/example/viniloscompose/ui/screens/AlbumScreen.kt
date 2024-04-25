package com.example.viniloscompose.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.viniloscompose.ui.navigation.BottomNavigation
import com.example.viniloscompose.ui.shared.ContentDescriptions

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AlbumScreen(navController: NavController) {
    Scaffold(
        bottomBar = {
            BottomNavigation(navController)
        },
        modifier = Modifier.semantics {
            contentDescription = ContentDescriptions.ALBUM_SCREEN.value
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Bienvenido Albumes",
                overflow = TextOverflow.Visible,
                modifier = Modifier
                    .width(180.dp)
                    .height(43.dp),
                style = TextStyle(
                    fontSize = 36.sp,
                    color = Color(0xFF1D1B20),
                    fontFamily = FontFamily.Default
                ),
                lineHeight = 42.sp
            )
        }
    }
}