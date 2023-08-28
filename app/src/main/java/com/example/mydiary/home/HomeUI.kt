package com.example.mydiary.home

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.mydiary.R
import com.example.mydiary.database.Diary
import com.example.mydiary.datetime.formatLongToDate
import com.example.mydiary.diary.BottomDiaryItem
import com.example.mydiary.ui.theme.Primary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeUI(
    onClickDiaryItem: (Diary) -> Unit,
    onClickEditDiary: (Diary) -> Unit
) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val scope = rememberCoroutineScope()
    val listDiaryItem by homeViewModel.listDiaryUI.collectAsState()

    val searchText by homeViewModel.searchText.collectAsState()

    LaunchedEffect(homeViewModel) {
        this.launch(Dispatchers.IO) { homeViewModel.getAllDiary() }
    }

    var showSearch by remember {
        mutableStateOf(false)
    }

    var showMenuSort by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        ToolBarHome(
            onClickSearch = {
                showSearch = !showSearch
                if (!showSearch) {
                    homeViewModel.onKeyWordChange("")
                }
            },
            onSortNewest = {
                homeViewModel.onSortNewest()
            },
            onSortOldest = {
                homeViewModel.onSortOldest()
            }
        )
        val grouped = listDiaryItem.groupBy {
            it.time.formatLongToDate("dd/MM/yyyy")
        }
        LazyColumn {
            item {
                if (showSearch) {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 2.dp,
                                color = Color.White,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .background(
                                color = Color.White.copy(alpha = 0.3f),
                                shape = RoundedCornerShape(16.dp)
                            ),
                        shape = RoundedCornerShape(16.dp),
                        value = searchText,
                        onValueChange = homeViewModel::onKeyWordChange,
                        leadingIcon = {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(id = R.drawable.ic_search),
                                contentDescription = null,
                                tint = Primary
                            )
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Primary,
                            backgroundColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        )
                    )
                }
            }
            grouped.forEach { (time, listDiary) ->
                stickyHeader {
                    Text(text = time, color = Color.Black)
                }
                items(listDiary) { diary ->
                    BottomDiaryItem(
                        diary = diary,
                        keyword = searchText,
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