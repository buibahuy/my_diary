package com.example.mydiary.nav

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.mydiary.diary.NewDiaryUI

fun NavGraphBuilder.addDiaryNavGraph(navController: NavController) {
    navigation(
        route = GRAPH.DIARY,
        startDestination = NewDiary.AddDiary.route
    ) {
        composable(NewDiary.AddDiary.route) {
            NewDiaryUI()
        }
    }
}

sealed class NewDiary(
    val route: String
) {
    object AddDiary : NewDiary(route = "add_diary")
    object OverViewDiary : NewDiary(route = "overview_diary")
}