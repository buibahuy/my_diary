package com.example.mydiary.diary

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun MoreOptionDiary() {
    val showMenu by remember {
        mutableStateOf(true)
    }
}