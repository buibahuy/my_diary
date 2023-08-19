package com.example.mydiary.diary

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mydiary.R
import com.example.mydiary.database.Diary
import com.example.mydiary.database.Mood
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun NewDiaryUI(
    mood: Mood = Mood.Default,
    onClickBack: () -> Unit = {},
    onClickPreview: () -> Unit = {},
    onClickSave: () -> Unit = {},
    onClickMood: () -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    val newDiaryViewModel: NewDiaryViewModel = hiltViewModel()
    val (title, onTitleChange) = remember { mutableStateOf("") }
    val (content, onContentChange) = remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        ToolBarDiary(
            onClickBack = onClickBack,
            onClickPreview = onClickPreview,
            onClickSave = {
                val diary = Diary(
                    title = title,
                    content = content,
                    mood = mood.icon,
                    time = 1,
                    background = 1,
                    tag = "1"
                )
                coroutineScope.launch(Dispatchers.IO) {
                    newDiaryViewModel.insertDiary(diary)
                }
            }
        )
        HeaderDiary(
            mood = mood,
            title = title,
            onTitleChange = onTitleChange,
            onClickMood = onClickMood
        )
        TextField(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = content,
            onValueChange = onContentChange,
            placeholder = {
                Text(text = "Enter content")
            }
        )
        BottomDiary(onClickBottomDiary = {

        })
    }
}

@Composable
fun HeaderDiary(
    title: String,
    onTitleChange: (String) -> Unit,
    mood: Mood,
    onClickMood: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Image(modifier = Modifier.clickable {
            onClickMood()
        }, painter = painterResource(id = mood.icon), contentDescription = null)
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 12.dp)
        ) {
            TextField(value = title,
                onValueChange = onTitleChange,
                placeholder = {
                    Text(text = "Title...")
                })
            Spacer(modifier = Modifier.size(20.dp))
            Row {
                Text(text = "Thứ 5, 5th1.2023")
                Icon(
                    painter = painterResource(id = R.drawable.ic_calendar),
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun BottomDiary(
    onClickBottomDiary: (BottomDiaryItem) -> Unit
) {
    val listBottomDiaryItem = listOf(
        BottomDiaryItem.BackGround,
        BottomDiaryItem.TextFont,
        BottomDiaryItem.Image,
        BottomDiaryItem.Tag,
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .background(
                color = Color.Gray,
                shape = RoundedCornerShape(10.dp)
            ), horizontalArrangement = Arrangement.SpaceAround
    ) {
        listBottomDiaryItem.forEach { item ->
            Column(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .clickable {
                        onClickBottomDiary(item)
                    }, horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(painter = painterResource(id = item.icon), contentDescription = null)
                Text(text = item.text)
            }
        }
    }
}