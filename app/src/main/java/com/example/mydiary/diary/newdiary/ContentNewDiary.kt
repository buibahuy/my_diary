package com.example.mydiary.diary.newdiary

import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mydiary.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ContentDiary(
    context: Context,
    scope: CoroutineScope,
    modifier: Modifier = Modifier,
    listImage: List<String>,
    onListUriChange: (List<Uri?>) -> Unit
) {
    val cameraPermissionState = rememberPermissionState(Manifest.permission.MEDIA_CONTENT_CONTROL)

    var imageUrisState by remember {
        mutableStateOf<List<Uri?>>(listImage.map { Uri.parse(it) })
    }

    var bitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }

    val multiplePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) {
        imageUrisState += it
        onListUriChange(imageUrisState)
    }
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
                    if (cameraPermissionState.status.isGranted) {
                        scope.launch(Dispatchers.IO) {
                            multiplePhotoPicker.launch(
                                "image/*"
                                //use for android 13
//                                PickVisualMediaRequest(
//                                    ActivityResultContracts.PickVisualMedia.ImageOnly
//                                )
                            )
                        }
                    } else {
                        cameraPermissionState.launchPermissionRequest()
                    }
                }, text = "open galery")
            }
            items(imageUrisState) {
                it?.let {
                    //use for android 13
                    context.contentResolver.takePersistableUriPermission(
                        it,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                    bitmap = if (Build.VERSION.SDK_INT < 28) {
                        MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                    } else {
                        val source = ImageDecoder.createSource(context.contentResolver, it)
                        ImageDecoder.decodeBitmap(source)
                    }
                }
                Image(bitmap = bitmap?.asImageBitmap()!!, contentDescription = null)
            }
        }
    }
}