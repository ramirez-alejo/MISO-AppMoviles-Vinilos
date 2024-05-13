package com.example.viniloscompose.ui.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.viniloscompose.ui.shared.BottomNavItem
import com.example.viniloscompose.ui.shared.ContentDescriptions

@Composable
fun BottomNavigation( onNavigate: (String) -> Unit, isSelected: (String) -> Boolean) {

    val items = listOf(
        BottomNavItem.Albums,
        BottomNavItem.Musicians,
        BottomNavItem.Collectors
    )

    NavigationBar(
        modifier = Modifier.semantics {
            contentDescription = ContentDescriptions.BOTTOM_NAVIGATION.value
        }
    ) {
        items.forEach { item ->
            AddItem(
                screen = item,
                onNavigate,
                isSelected
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomNavItem,
    onNavigate: (String) -> Unit,
    isSelected: (String) -> Boolean
) {
    NavigationBarItem(
        modifier = Modifier.semantics { contentDescription = screen.title },
        // Text that shows bellow the icon
        label = {
            Text(text = screen.title)
        },

        // The icon resource
        icon = {
            Icon(
                painterResource(id = screen.icon),
                contentDescription = screen.title,
                modifier = Modifier.size(20.dp)
            )
        },

        // Display if the icon it is select or not
        selected = isSelected(screen.rute), //currentDestination?.hierarchy?.any { it.route == screen.route } == true,

        // Always show the label bellow the icon or not
        alwaysShowLabel = true,

        // Click listener for the icon
        onClick = { onNavigate(screen.rute) },

        // Control all the colors of the icon
        colors = NavigationBarItemDefaults.colors()
    )
}

