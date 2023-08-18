package com.example.mydiary.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mydiary.bottombar.BottomBarItem
import com.example.mydiary.calender.CalenderUI
import com.example.mydiary.gallery.GalleryUI
import com.example.mydiary.home.HomeUI
import com.example.mydiary.setting.SettingUI

@Composable
fun NavigationHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomBarItem.Home.route
    ) {
        composable(BottomBarItem.Home.route) {
            HomeUI()
        }
        composable(BottomBarItem.Calender.route) {
            CalenderUI()
        }
        composable(BottomBarItem.Gallery.route) {
            GalleryUI()
        }
        composable(BottomBarItem.Setting.route) {
            SettingUI()
        }
    }
}