package com.example.test_demp

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object indices : BottomNavItem("indices",Icons.Filled.DateRange,"indices")
    object portfolio : BottomNavItem("search", Icons.Filled.Person, "portfolio")
}


@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.indices,
        BottomNavItem.portfolio,
    )

    // State to track the currently selected item
    var selectedItem by remember { mutableStateOf(items[0].route) }

    BottomNavigation(
        backgroundColor = Color.White, // General background color for BottomNavigation
        contentColor = Color.White,
        elevation = 8.dp
    ) {
        items.forEach { item ->
            val isSelected = item.route == selectedItem
            val selectedColor = when (item) {
                BottomNavItem.indices -> Color.Red // Red for indices
                BottomNavItem.portfolio -> Color.Blue // Blue for portfolio
                else -> Color.Gray // Default color for any other items
            }

            BottomNavigationItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = isSelected,
                selectedContentColor = selectedColor, // Apply specific color when selected
                unselectedContentColor = Color.Gray, // Unselected color
                onClick = {
                    selectedItem = item.route
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}