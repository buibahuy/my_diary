package com.example.mydiary.gallery

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.mydiary.datetime.formatLongToDate
import com.example.mydiary.home.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GalleryUI() {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val scope = rememberCoroutineScope()
    val listDiary by homeViewModel.listDiary.collectAsState()
    LaunchedEffect(homeViewModel) {
        this.launch(Dispatchers.IO) { homeViewModel.getAllDiary() }
    }

    val grouped = listDiary.groupBy {
        it.time
    }
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        grouped.forEach { (time, listDiary) ->
            stickyHeader {
                Text(text = time.formatLongToDate())
            }
            val listUri : MutableList<Uri> = mutableListOf()
            listDiary.forEach {diary->
                listUri.addAll( diary.photo.map {
                    Uri.parse(it)
                })
            }
            items(listUri){
                AsyncImage(model = it, contentDescription = null)
            }

        }
    }
}