package com.example.mydiary.home

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.mydiary.database.Diary
import com.example.mydiary.datetime.formatLongToDate
import com.example.mydiary.diary.BottomDiaryItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeUI(
    onClickDiaryItem: (Diary) -> Unit,
    onClickEditDiary: (Diary) -> Unit
) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val scope = rememberCoroutineScope()
    val listDiaryItem by homeViewModel.listDiary.collectAsState()
    LaunchedEffect(homeViewModel) {
        this.launch(Dispatchers.IO) { homeViewModel.getAllDiary() }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        ToolBarHome(onClickSearch = { /*TODO*/ }) {

        }
        val grouped = listDiaryItem.groupBy {
            it.time
        }
        LazyColumn {
            grouped.forEach { (time, listDiary) ->
                stickyHeader {
                    Text(text = time.formatLongToDate(), color = Color.Black)
                }
                items(listDiary) { diary ->
                    BottomDiaryItem(
                        diary = diary,
                        onClickDiaryItem = {
                            onClickDiaryItem(diary)
                        },
                        onClickDelete = {
                            scope.launch(Dispatchers.IO) {
                                homeViewModel.deleteDiary(diary = diary)
                            }
                        },
                        onClickEdit = {
                            onClickEditDiary(diary)
                        })
                    Spacer(modifier = Modifier.size(8.dp))
                }
            }
        }
    }
}