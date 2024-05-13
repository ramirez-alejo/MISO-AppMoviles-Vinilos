package com.example.viniloscompose.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.viniloscompose.R
import com.example.viniloscompose.ui.navigation.AppScreens
import com.example.viniloscompose.ui.shared.ContentDescriptions
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun InicioScreen(onNavigate: (String) -> Unit) {
    Scaffold(
        modifier = Modifier.semantics {
            contentDescription = ContentDescriptions.INICIO_SCREEN.value
        }
    ) {
        BodyContent(onNavigate)
    }
}

@Composable
fun BodyContent(onNavigate: (String) -> Unit) {
    val formattedDate = SimpleDateFormat(
        "EEEE dd MMMM yyyy",
        Locale.getDefault()
    ).format(Calendar.getInstance().time)
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            Text(formattedDate)
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(id = R.string.bienvenido),
                modifier = Modifier
                    .width(180.dp)
                    .height(43.dp),
                style = TextStyle(
                    fontSize = 36.sp,
                    color = Color(0xFF1D1B20),
                    fontFamily = FontFamily.Default,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                ),
                lineHeight = 42.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(id = R.string.seleccione_la_categoria),
                modifier = Modifier
                    .height(16.dp),
                style = TextStyle(
                    fontSize = 12.sp,
                ),
                lineHeight = 16.sp
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Column {
            Button(
                onClick = {
                    onNavigate(AppScreens.AlbumScreen.route)
                },
                modifier = Modifier
                    .height(40.dp)
                    .width(209.dp)
                    .semantics { contentDescription = ContentDescriptions.LOGIN_VISITOR.value }
            ) {
                Text(stringResource(id = R.string.visitante))
            }
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedButton(
                onClick = {
                    onNavigate(AppScreens.CollectorScreen.route)
                },
                modifier = Modifier
                    .height(40.dp)
                    .width(209.dp)
                    .semantics { contentDescription = ContentDescriptions.LOGIN_COLLECTOR.value }
            ) {
                Text(stringResource(id = R.string.coleccionista))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultInicioPreview() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = AppScreens.AlbumScreen.route) {
        composable(AppScreens.AlbumScreen.route) {
            InicioScreen(
                onNavigate = { dest -> navController.navigate(dest) },
            )
        }
    }
}
