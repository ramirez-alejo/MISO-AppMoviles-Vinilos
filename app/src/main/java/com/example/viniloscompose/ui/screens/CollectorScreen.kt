package com.example.viniloscompose.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.PlayArrow
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.viniloscompose.R
import com.example.viniloscompose.model.dto.CollectorDto
import com.example.viniloscompose.model.repository.CollectorRepository
import com.example.viniloscompose.model.service.mocks.CollectorServiceMock
import com.example.viniloscompose.ui.navigation.AppScreens
import com.example.viniloscompose.ui.navigation.BottomNavigation
import com.example.viniloscompose.ui.navigation.isSelectedBarItem
import com.example.viniloscompose.ui.shared.ContentDescriptions
import com.example.viniloscompose.utils.cache.FixedCacheManager
import com.example.viniloscompose.utils.network.FixedNetworkValidator
import com.example.viniloscompose.viewModel.CollectorViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CollectorScreen(
    onNavigate: (String) -> Unit,
    isSelected: (String) -> Boolean,
    collectorViewModel: CollectorViewModel
) {
    var query by remember { mutableStateOf("") }
    val state = collectorViewModel.state
    Scaffold(
        bottomBar = {
            BottomNavigation(onNavigate, isSelected)
        },
        modifier = Modifier.semantics {
            contentDescription = ContentDescriptions.COLLECTOR_SCREEN.value
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
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Lo sentimos, se ha presentado el siguiente error y no podemos atender tu solicitud en este momento: ${state.error}",
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = contentColorFor(Color.White),
                        fontFamily = FontFamily.Default,
                        textAlign = TextAlign.Center
                    )
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 64.dp, bottom = 84.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SearchBarCollector(onFilter = fun(newQuery: String) { query = newQuery })
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                ) {
                    TitleCollector()
                    Spacer(modifier = Modifier.height(12.dp))
                    val filteredCollectors = collectorViewModel.getFilteredCollectors(query)
                    BodyCollectorContent(filteredCollectors)
                }
            }
        }
    }
}

@Composable
fun TitleCollector() {
    Text(
        text = stringResource(id = R.string.coleccionistas_destacados),
        style = TextStyle(
            fontSize = 18.sp,
            color = Color(0xFF1D1B20),
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight(700),
            letterSpacing = 0.5.sp
        ),
        modifier = Modifier
            .semantics { contentDescription = ContentDescriptions.COLLECTOR_SCREEN_TITLE.value },
        lineHeight = 42.sp
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarCollector(onFilter: (String) -> Unit) {
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
            .semantics {
                contentDescription = ContentDescriptions.COLLECTOR_SCREEN_SEARCHBAR.value
            },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
        },
        placeholder = {
            Text(
                text = stringResource(id = R.string.buscar_coleccionista),
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
    ) {}
}

@Composable
fun BodyCollectorContent(collectors: List<CollectorDto>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .semantics { contentDescription = ContentDescriptions.COLLECTOR_SCREEN_BODY.value },

        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        itemsIndexed(items = collectors) { _, item ->
            CardCollector(item)
        }
    }
}


@Composable
fun CardCollector(item: CollectorDto) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 4.dp)
            .semantics { contentDescription = ContentDescriptions.COLLECTOR_CARD.value }
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
                    model = R.drawable.collector_icon,
                    contentDescription = item.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .semantics {
                            contentDescription = ContentDescriptions.COLLECTOR_CARD_IMAGE.value
                        }
                )
                Spacer(modifier = Modifier.size(4.dp))
                Column {
                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = stringResource(id = R.string.coleccionista),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .clickable { }
            )

        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultCollectorScreenPreview() {
    val navController = rememberNavController()
    val cacheManager = FixedCacheManager()
    val networkValidator = FixedNetworkValidator(true)
    NavHost(navController, startDestination = AppScreens.CollectorScreen.route) {
        composable(AppScreens.CollectorScreen.route) {
            CollectorScreen(
                onNavigate = { dest -> navController.navigate(dest) },
                isSelected = isSelectedBarItem(navController),
                CollectorViewModel(
                    CollectorRepository(
                        cacheManager,
                        networkValidator,
                        CollectorServiceMock()
                    )
                )
            )
        }
    }
}