package com.example.mydiary.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mydiary.database.Diary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun HomeUI() {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val listDiaryItem = remember {
        mutableStateOf(listOf<Diary>())
    }
    LaunchedEffect(homeViewModel) {
        this.launch(Dispatchers.IO) {
            listDiaryItem.value = homeViewModel.getAllDiary()
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            items(listDiaryItem.value) {
                Text(text = it.content ?: "111")
            }
        }
    }
}