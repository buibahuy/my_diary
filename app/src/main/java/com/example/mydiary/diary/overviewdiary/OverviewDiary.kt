package com.example.mydiary.diary.overviewdiary

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.mydiary.R
import com.example.mydiary.database.Diary
import com.example.mydiary.datetime.formatLongToDate
import com.example.mydiary.ui.theme.Primary
import com.example.mydiary.util.DashLine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun OverViewDiary(
    diary: Diary,
    isPreview: Boolean = true,
    onBackPress: () -> Unit,
    onClickDelete: () -> Unit,
    onEdit: (Diary) -> Unit
) {
    val overViewDiaryViewModel: OverViewDiaryViewModel = hiltViewModel()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End
    ) {
        Spacer(modifier = Modifier.size(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = "Preview",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            IconButton(
                onClick = {
                    onBackPress()
                },
                modifier = Modifier
                    .background(color = Color.White, shape = CircleShape)
                    .size(30.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_cancel),
                    contentDescription = null,
                    tint = Primary
                )
            }
        }
        Spacer(modifier = Modifier.size(12.dp))
        HeaderOverviewDiary(diary = diary)
        Spacer(modifier = Modifier.size(8.dp))
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .background(color = Color.White, shape = RoundedCornerShape(4.dp))
                .padding(16.dp)
        ) {
            item {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = diary.content ?: "",
                    textAlign = TextAlign.Left,
                    color = Color.Black
                )
            }
            items(diary.photo.map { Uri.parse(it) }) {
                it?.let {
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
        diary.contentSecond?.let {
            Text(text = it, color = Color.Black)
        }
        Spacer(modifier = Modifier.size(12.dp))
        if (isPreview) {
            BottomOverviewDiary(onClickDelete = {
                coroutineScope.launch(Dispatchers.IO) {
                    overViewDiaryViewModel.deleteDiary(diary)
                }
                onClickDelete()
            }, onEdit = { onEdit(diary) })
        }
    }
}

@Composable
fun HeaderOverviewDiary(diary: Diary) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(4.dp))
            .padding(vertical = 8.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Image(modifier = Modifier.size(40.dp),painter = painterResource(id = diary.mood), contentDescription = null)
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 12.dp)
        ) {
            Text(text = diary.title ?: "",color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.size(10.dp))
            DashLine(modifier = Modifier.padding(start = 16.dp), color = Color.Black)
            Spacer(modifier = Modifier.size(10.dp))
            Text(text = diary.time?.formatLongToDate() ?: "",color = Color.Black)
        }
    }
}

@Composable
fun BottomOverviewDiary(
    onClickDelete: () -> Unit,
    onEdit: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .border(width = 2.dp, color = Primary, shape = RoundedCornerShape(16.dp))
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp)
                )
                .clickable {
                    onClickDelete()
                }
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.ic_delete),
                contentDescription = null,
                tint = Primary
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(text = "Delete", color = Primary, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.size(8.dp))
        Row(
            modifier = Modifier
                .weight(1f)
                .background(
                    color = Primary,
                    shape = RoundedCornerShape(16.dp)
                )
                .clickable {
                    onEdit()
                }
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.ic_edit),
                contentDescription = null
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(text = "Edit", fontWeight = FontWeight.Bold)
        }
    }
}