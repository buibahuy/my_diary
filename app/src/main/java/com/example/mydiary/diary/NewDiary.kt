package com.example.mydiary.diary

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.text.format.Time
import android.util.Log
import android.widget.DatePicker
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mydiary.R
import com.example.mydiary.database.Diary
import com.example.mydiary.database.Mood
import com.example.mydiary.datetime.formatLongToDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@SuppressLint("SimpleDateFormat")
@Composable
fun NewDiaryUI(
    diary: Diary? = null,
    mood: Mood = Mood.Default,
    onClickBack: () -> Unit = {},
    onClickPreview: () -> Unit = {},
    onClickSave: () -> Unit = {},
    onClickMood: () -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    val mCalendar = Calendar.getInstance()
    val mContext = LocalContext.current
    val newDiaryViewModel: NewDiaryViewModel = hiltViewModel()
    val (title, onTitleChange) = remember {
        mutableStateOf(if (diary != null) diary.title else "")
    }
    val (content, onContentChange) = remember {
        mutableStateOf(if (diary != null) diary.content else "")
    }
    var time by remember {
        mutableStateOf(if (diary != null) diary.time else mCalendar.time.time)
    }

    val mDate = remember {
        val timeFormat = if (diary != null)
            diary.time
        else mCalendar.time.time

        mutableStateOf(timeFormat.formatLongToDate())
    }

    Column(modifier = Modifier.fillMaxSize()) {
        ToolBarDiary(
            onClickBack = onClickBack,
            onClickPreview = onClickPreview,
            onClickSave = {
                val diary1 = Diary(
                    title = title,
                    content = content,
                    mood = mood.icon,
                    time = time,
                    background = 1,
                    tag = "1"
                )
                coroutineScope.launch(Dispatchers.IO) {
                    newDiaryViewModel.insertDiary(diary1)
                }
            }
        )

        val mYear: Int = mCalendar.get(Calendar.YEAR)
        val mMonth: Int = mCalendar.get(Calendar.MONTH)
        val mDay: Int = mCalendar.get(Calendar.DAY_OF_MONTH)

        HeaderDiary(
            mood = mood,
            title = title ?: "",
            time = mDate.value,
            onTitleChange = onTitleChange,
            onClickMood = onClickMood,
            onClickTime = {
                val mDatePickerDialog = DatePickerDialog(
                    mContext,
                    { _: DatePicker, year: Int, month: Int, mDayOfMonth: Int ->
                        mCalendar.set(year, month, mDayOfMonth)
                        val long = mCalendar.time.time
                        val dayOfWeek: String = mCalendar.time.time.formatLongToDate()
                        mDate.value = dayOfWeek
                        time = long
                    }, mYear, mMonth, mDay
                )
                mDatePickerDialog.show()
            }
        )
        TextField(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = content ?: "",
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
    time: String,
    onClickMood: () -> Unit,
    onClickTime: () -> Unit
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
            Row(
                modifier = Modifier.clickable {
                    onClickTime()
                }, horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = time)
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