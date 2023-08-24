//package com.example.mydiary.diary.newdiary
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.WindowInsets
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.grid.GridCells
//import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
//import androidx.compose.foundation.lazy.grid.items
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.TextField
//import androidx.compose.material.TextFieldDefaults
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.ModalBottomSheet
//import androidx.compose.material3.SheetState
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.*
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.example.mydiary.R
//import com.example.mydiary.mood.ItemMoodBottomSheet
//import com.example.mydiary.mood.Mood
//import com.example.mydiary.ui.theme.Primary
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun BottomSheetMood(
//    showModalSheet : Boolean,
//    bottomSheetState : SheetState
//) {
//    var showModalSheetState by remember {
//        mutableStateOf(showModalSheet)
//    }
//
//    ModalBottomSheet(
//        modifier = Modifier
//            .padding(bottom = 56.dp)
//            .fillMaxWidth(),
//        windowInsets = WindowInsets(bottom = 48.dp),
//        onDismissRequest = {
//            showModalSheetState = false
//        },
//        sheetState = bottomSheetState
//    ) {
//        val (textSearch, onTextSearchChange) = remember {
//            mutableStateOf("")
//        }
//        Text(
//            modifier = Modifier.fillMaxWidth(),
//            text = "How are you today?",
//            textAlign = TextAlign.Center
//        )
//        TextField(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 16.dp, vertical = 12.dp)
//                .clip(
//                    RoundedCornerShape(10.dp)
//                ), colors = TextFieldDefaults.textFieldColors(
//                unfocusedIndicatorColor = Color.Transparent,
//                focusedIndicatorColor = Color.Transparent
//            ),
//            value = textSearch,
//            onValueChange = onTextSearchChange,
//            leadingIcon = {
//                Icon(
//                    painter = painterResource(id = R.drawable.ic_search),
//                    contentDescription = null
//                )
//            },
//            placeholder = {
//                Text(text = "Search")
//            }
//        )
//
//        val listMood = Mood.getListMood()
//
//        LazyVerticalGrid(modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp, vertical = 12.dp),
//            columns = GridCells.Fixed(4),
//            verticalArrangement = Arrangement.spacedBy(8.dp),
//            horizontalArrangement = Arrangement.spacedBy(16.dp),
//            content = {
//                items(listMood) { item: Mood ->
//                    val isSelected = selectedMood == item.icon
//                    val modifierSelected = if (isSelected) Modifier.border(
//                        width = if (isSelected) 2.dp else 0.dp,
//                        color = Primary,
//                        shape = RoundedCornerShape(12.dp)
//                    ) else Modifier
//                    ItemMoodBottomSheet(
//                        modifier = modifierSelected,
//                        isSelected = item.icon == selectedMood,
//                        mood = item,
//                        onClickMood = {
//                            selectedMood = item.icon
//                        })
//                }
//            }
//        )
//        Text(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 16.dp, horizontal = 16.dp)
//                .clickable {
//                    showModalSheet = false
//                }
//                .background(color = Primary, shape = RoundedCornerShape(10.dp))
//                .padding(vertical = 8.dp, horizontal = 16.dp),
//            text = "Apply",
//            textAlign = TextAlign.Center,
//            fontWeight = FontWeight.Bold,
//            fontSize = 16.sp,
//            color = Color.White
//        )
//    }
//}
//
