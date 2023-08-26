package com.example.mydiary.diary

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mydiary.R
import com.example.mydiary.database.Diary
import com.example.mydiary.datetime.formatLongToDate
import com.example.mydiary.mood.Mood
import com.example.mydiary.ui.theme.Primary

@SuppressLint("ResourceType")
@Composable
fun BottomDiaryItem(
    diary: Diary,
    onClickDiaryItem: (Diary) -> Unit,
    onClickDelete: () -> Unit,
    onClickEdit: (Diary) -> Unit,
) {
    var showMenu by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(10.dp))
            .clickable {
                onClickDiaryItem(diary)
            }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(painter = painterResource(id = diary.mood ?: Mood.Default.icon), contentDescription = null)
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = diary.title ?: "", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(text = diary.time?.formatLongToDate().toString(), color = Color.Black, fontSize = 16.sp)
        }
        Box(modifier = Modifier) {
            Icon(
                modifier = Modifier.clickable {
                    showMenu = !showMenu
                },
                painter = painterResource(id = R.drawable.ic_more_option),
                contentDescription = null,
                tint = Primary
            )
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }) {
                DropdownMenuItem(onClick = {
                    onClickEdit(diary)
                }, text = {
                    Text(text = "Edit")
                })
                DropdownMenuItem(onClick = {
                    onClickDelete()
                }, text = {
                    Text(text = "Delete")
                })
            }
        }

    }
}