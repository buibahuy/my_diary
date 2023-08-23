package com.example.mydiary.diary.newdiary

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mydiary.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ContentDiary(
    scope: CoroutineScope,
    modifier: Modifier = Modifier,
    listImage: List<String>,
    onListUriChange: (List<Uri>) -> Unit
) {
    var imageUrisState by remember {
        mutableStateOf<List<Uri>>(listImage.map { Uri.parse(it) })
    }

    val multiplePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = {
            imageUrisState += it
            onListUriChange(imageUrisState)
        }
    )
    Box(modifier = modifier.padding(horizontal = 16.dp))
    {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.background_content_default_theme),
            contentDescription = "background_image",
            contentScale = ContentScale.FillBounds
        )
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                Text(modifier = Modifier.clickable {
                    scope.launch {
                        multiplePhotoPicker.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                }, text = "open galery")
            }
            items(imageUrisState) {
                AsyncImage(model = it, contentDescription = null)
            }
        }
    }
}