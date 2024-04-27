package com.example.viniloscompose.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
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
import com.example.viniloscompose.ui.navigation.AppScreens
import com.example.viniloscompose.ui.shared.ContentDescriptions
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun InicioScreen( onNavigate: (String) -> Unit ) {
    Scaffold(
        modifier = Modifier.semantics {
            contentDescription = ContentDescriptions.INICIO_SCREEN.value
        }
    ) {
        BodyContent(onNavigate)
    }
}

@Composable
fun BodyContent( onNavigate: (String) -> Unit) {
    val formattedDate = SimpleDateFormat(
        "EEEE dd MMMM yyyy",
        Locale.getDefault()
    ).format(Calendar.getInstance().time)
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(formattedDate)
        Text(
            text = "Bienvenido",
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
        Text(
            text = "Selecciona la categoría a la que perteneces",
            modifier = Modifier
                .width(232.dp)
                .height(16.dp),
            overflow = TextOverflow.Visible,
            style = TextStyle(
                fontSize = 12.sp,
                color = Color(0xFF1D1B20),
                fontFamily = FontFamily.Default
            ),
            lineHeight = 16.sp,
            letterSpacing = 2.sp
        )
        Spacer(modifier = Modifier.height(25.dp))
        Button(
            onClick = {
                onNavigate(AppScreens.AlbumScreen.route)
            },
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .height(40.dp)
                .width(200.dp)
                .padding(end = 8.dp)
                .semantics { contentDescription = ContentDescriptions.LOGIN_VISITOR.value }
        ) {
            Text("Visitante")
        }
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedButton(
            onClick = {
                onNavigate(AppScreens.CollectorScreen.route)
            },
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .height(40.dp)
                .width(200.dp)
                .padding(end = 8.dp)
                .semantics { contentDescription = ContentDescriptions.LOGIN_COLLECTOR.value }
        ) {
            Text("Coleccionista")
        }
    }
}
