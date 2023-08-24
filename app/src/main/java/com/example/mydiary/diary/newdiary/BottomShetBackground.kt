package com.example.mydiary.diary.newdiary

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mydiary.R
import com.example.mydiary.ui.theme.Primary
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBarBackground(
    backgroundSelected: Int,
    onBackgroundChange: (Int) -> Unit,
    bottomSheetState: SheetState,
    scope: CoroutineScope
) {
    val listBackground = listOf(
        R.drawable.background_1,
        R.drawable.background_2,
        R.drawable.background_3,
        R.drawable.background_4,
        R.drawable.background_5,
        R.drawable.background_6,
        R.drawable.background_7,
    )
    if (bottomSheetState.isVisible) {
        ModalBottomSheet(
            modifier = Modifier
                .padding(bottom = 56.dp)
                .fillMaxWidth(),
            onDismissRequest = {
                scope.launch {
                    bottomSheetState.hide()
                }
            },
            sheetState = bottomSheetState
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "How are you today?",
                textAlign = TextAlign.Center
            )

            LazyVerticalGrid(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
                columns = GridCells.Fixed(4),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                content = {
                    items(listBackground) { item: Int ->
                        val isSelected = backgroundSelected == item
                        val modifierSelected = if (isSelected) Modifier.border(
                            width = if (isSelected) 2.dp else 0.dp,
                            color = Primary,
                            shape = RoundedCornerShape(12.dp)
                        ) else Modifier
                        Image(
                            modifier = modifierSelected
                                .clip(RoundedCornerShape(8.dp))
                                .clickable {
                                    onBackgroundChange(item)
                                },
                            painter = painterResource(id = item),
                            contentDescription = null
                        )
                    }
                }
            )
        }
    }
}