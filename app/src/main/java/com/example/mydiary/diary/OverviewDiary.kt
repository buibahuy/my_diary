package com.example.mydiary.diary

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mydiary.R
import com.example.mydiary.database.Diary
import com.example.mydiary.datetime.formatLongToDate

@Composable
fun OverViewDiary(
    diary: Diary
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        IconButton(
            onClick = {},
            modifier = Modifier
                .background(color = Color.Blue, shape = CircleShape)
                .size(30.dp)
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_cancel), contentDescription = null)
        }
        Spacer(modifier = Modifier.size(16.dp))
        HeaderOverviewDiary(diary = diary)
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(color= Color.Gray, shape = RoundedCornerShape(4.dp))
                .padding(16.dp),
            text = diary.content ?: ""
        )
        Spacer(modifier = Modifier.size(12.dp))
        BottomOverviewDiary(
            onClickDelete = {},
            onEdit = {}
        )
    }
}

@Composable
fun HeaderOverviewDiary(diary: Diary) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color= Color.Gray, shape = RoundedCornerShape(4.dp))
            .padding(vertical = 8.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Image(painter = painterResource(id = diary.mood), contentDescription = null)
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 12.dp)
        ) {
            Text(text = diary.title ?: "")
            Spacer(modifier = Modifier.size(20.dp))
            Text(text = diary.time?.formatLongToDate() ?: "")
        }
    }
}

@Composable
fun BottomOverviewDiary(
    onClickDelete: () -> Unit,
    onEdit: () -> Unit,
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .weight(1f)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp)
                )
                .border(width = 2.dp, color = Color.Blue, shape = RoundedCornerShape(16.dp))
                .clickable {
                    onClickDelete()
                }
                .padding(vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_delete), contentDescription = null)
            Text(text = "Delete")
        }
        Spacer(modifier = Modifier.size(8.dp))
        Row(
            modifier = Modifier
                .weight(1f)
                .background(
                    color = Color.Blue,
                    shape = RoundedCornerShape(16.dp)
                )
                .clickable {
                    onClickDelete()
                }
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_edit), contentDescription = null)
            Text(text = "Edit")
        }
    }
}

@Preview
@Composable
fun PreviewDiary() {
    BottomOverviewDiary(
        onClickDelete = {},
        onEdit = {}
    )
}