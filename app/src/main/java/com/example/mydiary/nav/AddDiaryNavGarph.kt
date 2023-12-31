package com.example.mydiary.nav

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.mydiary.bottombar.BottomBarItem
import com.example.mydiary.database.Diary
import com.example.mydiary.diary.newdiary.NewDiaryUI
import com.example.mydiary.diary.overviewdiary.OverViewDiary
import com.google.gson.Gson
import okio.ByteString.Companion.encode
import java.net.URLEncoder

fun NavGraphBuilder.addDiaryNavGraph(navController: NavController) {
    navigation(
        route = GRAPH.DIARY,
        startDestination = NewDiary.AddDiary.route
    ) {
        composable(NewDiary.AddDiary.route,
            arguments = listOf(
                navArgument("diary") {
                    type = NavType.StringType
                }
            )) {
            val diaryJson = it.arguments!!.getString("diary")
            val diary = Gson().fromJson(diaryJson, Diary::class.java)
            NewDiaryUI(
                diary = diary,
                onClickBack = { navController.navigateUp() },
                onClickSave = { navController.navigate(BottomBarItem.Home.route) },
                onClickPreview = { diaryPreview->
                    navController.navigate(NewDiary.OverViewDiary.navigateWithDiary(diaryPreview,isPreview = false)){
                        restoreState = true
                    }
                }
            )
        }
        composable(NewDiary.OverViewDiary.route,
            arguments = listOf(
                navArgument("diary") {
                    type = NavType.StringType
                },navArgument("isPreview") {
                    type = NavType.BoolType
                }
            )
        ) {
            val diaryJson = it.arguments!!.getString("diary")
            val diary = Gson().fromJson(diaryJson, Diary::class.java)

            val isPreview = it.arguments!!.getBoolean("isPreview")

            Log.d("Tag1234","---$diary")
            OverViewDiary(
                diary = diary,
                isPreview = isPreview,
                onBackPress = { navController.navigateUp() },
                onEdit = { diaryNav ->
                    navController.navigate(NewDiary.AddDiary.navigateWithDiary(diaryNav))
                }, onClickDelete = {
                    navController.navigateUp()
                })
        }
    }
}

sealed class NewDiary(
    val route: String
) {
    object AddDiary : NewDiary(route = "add_diary/{diary}") {
        fun navigateWithDiary(diary: Diary): String {
            val gson = Gson()
            val json = gson.toJson(diary)
            val jsonencode = URLEncoder.encode(json, "utf-8")
            return "add_diary/$jsonencode"
        }
    }

    object OverViewDiary : NewDiary(route = "overview_diary/{diary}/{isPreview}") {
        fun navigateWithDiary(diary: Diary, isPreview:Boolean): String {
            val gson = Gson()
            val json = gson.toJson(diary)
            val jsonencode = URLEncoder.encode(json, "utf-8")
            return "overview_diary/$jsonencode/$isPreview"
        }
    }
}