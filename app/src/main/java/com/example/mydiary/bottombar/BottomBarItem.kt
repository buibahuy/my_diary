package com.example.mydiary.bottombar

import com.example.mydiary.R

sealed class BottomBarItem(
    val icon: Int,
    val route: String,
) {
    object Home : BottomBarItem(icon = R.drawable.ic_home, route = "home")
    object Calender : BottomBarItem(icon = R.drawable.ic_calendar, route = "calender")
    object Space : BottomBarItem(icon = R.drawable.ic_calendar, route = "space")
    object Gallery : BottomBarItem(icon = R.drawable.ic_gallery, route = "gallery")
    object Setting : BottomBarItem(icon = R.drawable.ic_setting, route = "setting")
}
