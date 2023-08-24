package com.example.mydiary.diary.newdiary

import android.Manifest
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.DatePicker
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mydiary.R
import com.example.mydiary.database.Diary
import com.example.mydiary.datetime.formatLongToDate
import com.example.mydiary.diary.BottomDiaryItem
import com.example.mydiary.diary.ToolBarDiary
import com.example.mydiary.mood.ItemMoodBottomSheet
import com.example.mydiary.mood.Mood
import com.example.mydiary.ui.theme.Primary
import com.example.mydiary.util.DashLine
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@SuppressLint("SimpleDateFormat", "MutableCollectionMutableState")
@Composable
fun NewDiaryUI(
    diary: Diary? = null,
    onClickBack: () -> Unit = {},
    onClickPreview: (Diary) -> Unit = {},
    onClickSave: () -> Unit = {},
    onClickMood: () -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    val mCalendar = Calendar.getInstance()
    val mContext = LocalContext.current
    val newDiaryViewModel: NewDiaryViewModel = hiltViewModel()
    val focusManager = LocalFocusManager.current

    val bottomSheetState = rememberModalBottomSheetState()

    val bottomSheetStateBackground = rememberModalBottomSheetState()

    var backgroundSelected by rememberSaveable {
        mutableIntStateOf(R.drawable.background_1)
    }

    var showModalSheet by rememberSaveable {
        mutableStateOf(diary?.mood == null)
    }

    val (title, onTitleChange) = rememberSaveable {
        mutableStateOf(if (diary != null) diary.title else "")
    }
    val (content, onContentChange) = rememberSaveable {
        mutableStateOf(if (diary != null) diary.content else "")
    }

    val (contentSecond, onContentSecondChange) = rememberSaveable {
        mutableStateOf(if (diary != null) diary.contentSecond else "")
    }

    var time by rememberSaveable {
        mutableStateOf(if (diary != null) diary.time else mCalendar.time.time)
    }

    var selectedMood by rememberSaveable {
        mutableIntStateOf(diary?.mood ?: Mood.Default.icon)
    }

    var listTag by rememberSaveable {
        mutableStateOf(diary?.listTag?: listOf())
    }

    val mDate = rememberSaveable {
        val timeFormat = if (diary != null)
            diary.time
        else mCalendar.time.time

        mutableStateOf(timeFormat.formatLongToDate())
    }

    var imageUrisStateString by rememberSaveable {
        mutableStateOf(diary?.photo ?: emptyList())
    }

    val cameraPermissionState =
        rememberPermissionState(Manifest.permission.READ_EXTERNAL_STORAGE)

    var imageUrisState by rememberSaveable {
        mutableStateOf<List<Uri?>>(imageUrisStateString.map { Uri.parse(it) })
    }
    var bitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }

    LaunchedEffect(imageUrisState) {
        imageUrisStateString = imageUrisState.map { uri -> uri.toString() }
        imageUrisState.forEach { uri ->
            if (uri != null)
                mContext.contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
        }
    }
    val multiplePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenMultipleDocuments()
    ) {
        imageUrisState += it
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .clickable { focusManager.clearFocus() }) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(backgroundSelected),
            contentDescription = "background_image",
            contentScale = ContentScale.FillBounds
        )

        Column(modifier = Modifier.fillMaxSize()) {
            val diary1 = Diary(
                title = title,
                content = content,
                contentSecond = contentSecond,
                mood = selectedMood,
                time = time,
                photo = imageUrisStateString,
                background = 1,
                listTag = listTag.toList()
            )
            ToolBarDiary(
                onClickBack = onClickBack,
                onClickPreview = { onClickPreview(diary1) },
                onClickSave = {

                    coroutineScope.launch(Dispatchers.IO) {
                        if (diary == null)
                            newDiaryViewModel.insertDiary(diary1)
                        else {
                            newDiaryViewModel.updateDiary(diary1.copy(id = diary.id))
                        }
                    }
                    onClickSave()
                }
            )

            val mYear: Int = mCalendar.get(Calendar.YEAR)
            val mMonth: Int = mCalendar.get(Calendar.MONTH)
            val mDay: Int = mCalendar.get(Calendar.DAY_OF_MONTH)

            HeaderDiary(
                mood = selectedMood,
                title = title ?: "",
                time = mDate.value,
                onTitleChange = onTitleChange,
                onClickMood = {
                    showModalSheet = true
                },
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

            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            )
            {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(R.drawable.background_content_default_theme),
                    contentDescription = "background_image",
                    contentScale = ContentScale.FillBounds
                )

                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item {
                        TextField(
                            value = content ?: "",
                            placeholder = {
                                Text(text = "Enter content...")
                            },
                            onValueChange = onContentChange,
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent
                            ),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
                        )
                    }
                    items(imageUrisState) {
                        it?.let {
                            bitmap = if (Build.VERSION.SDK_INT < 28) {
                                MediaStore.Images.Media.getBitmap(mContext.contentResolver, it)
                            } else {
                                val source = ImageDecoder.createSource(mContext.contentResolver, it)
                                ImageDecoder.decodeBitmap(source)
                            }
                        }
                        Image(bitmap = bitmap?.asImageBitmap()!!, contentDescription = null)
                    }
                    if (imageUrisState.isNotEmpty()) {
                        item {
                            TextField(
                                value = contentSecond ?: "",
                                placeholder = {
                                    Text(text = "Enter content...")
                                },
                                onValueChange = onContentSecondChange,
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent
                                ),
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
                            )
                        }
                    }
                    item {
                        LazyRow(modifier = Modifier.fillMaxWidth()) {
                            itemsIndexed(listTag) { index : Int, tag: String ->
                                val (tagChange, onTagChange) = remember {
                                    mutableStateOf(tag)
                                }
                                TextField(
                                    modifier = Modifier
                                        .padding(horizontal = 4.dp)
                                        .background(
                                            color = Color.Black.copy(alpha = 0.5f),
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .widthIn(1.dp, Dp.Infinity),
                                    value = tagChange,
                                    trailingIcon = {
                                        IconButton(
                                            onClick = {
                                                val mutableList = listTag.toMutableList()
                                                mutableList.removeAt(index)
                                                listTag = mutableList.toList()
                                            }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Clear,
                                                contentDescription = "Clear"
                                            )
                                        }
                                    },
                                    onValueChange = onTagChange,
                                    colors = TextFieldDefaults.textFieldColors(
                                        backgroundColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent,
                                        focusedIndicatorColor = Color.Transparent
                                    ),
                                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                                )
                            }
                        }
                    }
                }
            }

            BottomDiary(onClickBottomDiary = { bottomDiaryItem ->
                when (bottomDiaryItem) {
                    BottomDiaryItem.BackGround -> {
                        coroutineScope.launch {
                            bottomSheetStateBackground.show()
                        }
                    }

                    BottomDiaryItem.TextFont -> {

                    }

                    BottomDiaryItem.Image -> {
                        if (cameraPermissionState.status.isGranted) {
                            coroutineScope.launch(Dispatchers.IO) {
                                multiplePhotoPicker.launch(arrayOf("image/*"))
                            }
                        } else {
                            cameraPermissionState.launchPermissionRequest()
                        }
                    }

                    BottomDiaryItem.Tag -> {
                        listTag += ""
                    }
                }
            })

            if (bottomSheetStateBackground.isVisible) {
                BottomBarBackground(
                    backgroundSelected = backgroundSelected,
                    onBackgroundChange = {
                        backgroundSelected = it
                    },
                    bottomSheetState = bottomSheetStateBackground,
                    scope = coroutineScope
                )
            }
            if (showModalSheet) {
                ModalBottomSheet(
                    modifier = Modifier
                        .padding(bottom = 56.dp)
                        .fillMaxWidth(),
                    onDismissRequest = {
                        showModalSheet = false
                    },
                    sheetState = bottomSheetState
                ) {
                    val (textSearch, onTextSearchChange) = remember {
                        mutableStateOf("")
                    }
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "How are you today?",
                        textAlign = TextAlign.Center
                    )
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                            .clip(
                                RoundedCornerShape(10.dp)
                            ), colors = TextFieldDefaults.textFieldColors(
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        ),
                        value = textSearch,
                        onValueChange = onTextSearchChange,
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_search),
                                contentDescription = null
                            )
                        },
                        placeholder = {
                            Text(text = "Search")
                        }
                    )

                    val listMood = Mood.getListMood()

                    LazyVerticalGrid(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                        columns = GridCells.Fixed(4),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        content = {
                            items(listMood) { item: Mood ->
                                val isSelected = selectedMood == item.icon
                                val modifierSelected = if (isSelected) Modifier.border(
                                    width = if (isSelected) 2.dp else 0.dp,
                                    color = Primary,
                                    shape = RoundedCornerShape(12.dp)
                                ) else Modifier
                                ItemMoodBottomSheet(
                                    modifier = modifierSelected,
                                    isSelected = item.icon == selectedMood,
                                    mood = item,
                                    onClickMood = {
                                        selectedMood = item.icon
                                    })
                            }
                        }
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp, horizontal = 16.dp)
                            .clickable {
                                showModalSheet = false
                            }
                            .background(color = Primary, shape = RoundedCornerShape(10.dp))
                            .padding(vertical = 8.dp, horizontal = 16.dp),
                        text = "Apply",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun HeaderDiary(
    title: String,
    onTitleChange: (String) -> Unit,
    mood: Int,
    time: String,
    onClickMood: () -> Unit,
    onClickTime: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 12.dp)
            .background(color = Color.White, shape = RoundedCornerShape(10.dp))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Image(modifier = Modifier
            .clickable {
                onClickMood()
            }
            .size(40.dp), painter = painterResource(id = mood), contentDescription = null)
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 12.dp)
        ) {
            TextField(
                value = title,
                onValueChange = onTitleChange,
                placeholder = {
                    Text(text = "Title...")
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                )
            )
            Spacer(modifier = Modifier.size(10.dp))
            DashLine(modifier = Modifier.padding(start = 16.dp), color = Color.Black)
            Spacer(modifier = Modifier.size(10.dp))
            Row(
                modifier = Modifier
                    .clickable {
                        onClickTime()
                    }
                    .padding(start = 16.dp), horizontalArrangement = Arrangement.Start,
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
                color = Primary,
                shape = RoundedCornerShape(10.dp)
            )
            .border(width = 1.dp, color = Color.White, shape = RoundedCornerShape(10.dp)),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        listBottomDiaryItem.forEach { item ->
            Column(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .clickable {
                        onClickBottomDiary(item)
                    }
                    .padding(vertical = 4.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = item.icon),
                    contentDescription = null,
                    tint = Color.White
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(text = item.text, color = Color.White)
            }
        }
    }
}