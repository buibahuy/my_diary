package com.example.mydiary.bottombar

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.mydiary.ui.theme.Gray40
import com.example.mydiary.ui.theme.Primary

@Composable
fun currentRoute(navController: NavController): String? {
    val currentRoute by navController.currentBackStackEntryAsState()
    return currentRoute?.destination?.route
}

@Composable
fun BottomBar(navController: NavController, listBarItem: List<BottomBarItem>) {

    BottomAppBar(
        contentPadding = PaddingValues(0.dp), cutoutShape = MaterialTheme.shapes.medium.copy(
            CornerSize(percent = 50)
        )
    ) {
        BottomNavigation(modifier = Modifier.fillMaxSize(), backgroundColor = Color.White) {
            listBarItem.forEach { bottomBarItem ->
                val currentRoute = currentRoute(navController = navController)
                BottomNavigationItem(
                    selected = currentRoute == bottomBarItem.route,
                    onClick = { navController.navigate(bottomBarItem.route) },
                    icon = {
                        Icon(
                            painter = painterResource(id = bottomBarItem.icon),
                            contentDescription = null,
                            tint = if (currentRoute == bottomBarItem.route) Primary else Gray40
                        )
                    },
                    alwaysShowLabel = false,
                    enabled = bottomBarItem.route != BottomBarItem.Space.route
                )
            }
        }
    }
}

