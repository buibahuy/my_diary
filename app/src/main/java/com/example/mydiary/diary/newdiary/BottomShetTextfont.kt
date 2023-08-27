package com.example.mydiary.diary.newdiary

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mydiary.R
import com.example.mydiary.ui.theme.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetTextFont(
    fontSizeSelected: TextUnit,
    positionSelected: TextAlign,
    colorSelected: Color,
    fontSelected: FontFamily,
    onFontSizeChange: (TextUnit) -> Unit,
    onPositionChange: (TextAlign) -> Unit,
    onColorChange: (Color) -> Unit,
    onFontChange: (FontFamily) -> Unit,
    bottomSheetState: SheetState,
    scope: CoroutineScope
) {

    val listFontSize = listOf(
        ItemBottomFont.FontSmall,
        ItemBottomFont.FontMedium,
        ItemBottomFont.FontLarge
    )

    val listPosition = listOf(
        ItemBottomFont.PositionMiddle,
        ItemBottomFont.PositionLeft,
        ItemBottomFont.PositionRight,
    )
    val listColor = listOf(
        Colorblack100,
        Colorblack50,
        Colorblack10,
        Colorpurple100,
        Colorpurple50,
        Colorpurple10,
        Colorred100,
        Colorred50,
        Colorred10,
        Colorpink100,
        Colorpink50,
        Colorpink10,
        Colororange100,
        Colororange50,
        Colororange10,
        Coloryellow100,
        Coloryellow50,
        Coloryellow10,
        Colorgreen100,
        Colorgreen50,
        Colorgreen10,
        Colorgreen90,
        Colorgreen60,
        Colorgreen30,
        Colorblue100,
        Colorblue50,
        Colorblue10,
        Colordarkblue100,
        Colordarkblue50,
        Colordarkblue10,
    )

    val listFont = listOf(
        WindSongFont,
        Akayate,
        Alison,
        Vibur
    )
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "Font",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.size(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                LazyRow(
                    modifier = Modifier
                        .weight(1f)
                        .background(color = Color.Red, shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 24.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(listFontSize) {
                        Icon(
                            modifier = Modifier
                                .size(16.dp)
                                .clickable {
                                    onFontSizeChange(it.value as TextUnit)
                                },
                            painter = painterResource(id = it.icon),
                            contentDescription = null
                        )
                    }
                }
                Spacer(modifier = Modifier.size(4.dp))
                LazyRow(
                    modifier = Modifier
                        .weight(1f)
                        .background(color = Color.Red, shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 24.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(listPosition) {
                        Icon(
                            modifier = Modifier
                                .size(16.dp)
                                .clickable {
                                    onPositionChange(it.value as TextAlign)
                                },
                            painter = painterResource(id = it.icon),
                            contentDescription = null
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.size(16.dp))
            LazyRow(modifier = Modifier) {
                items(listColor) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .clip(CircleShape)
                            .size(40.dp)
                            .background(color = it)
                            .clickable {
                                onColorChange(it)
                            }
                    )
                }
            }
            Spacer(modifier = Modifier.size(16.dp))
            LazyVerticalGrid(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                content = {
                    items(listFont) {
                        Text(
                            modifier = Modifier
                                .background(
                                    color = Color.Green,
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .padding(vertical = 8.dp)
                                .clickable {
                                    onFontChange(it)
                                },
                            text = "Font",
                            fontFamily = it,
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.size(48.dp))
        }
    }
}

sealed class ItemBottomFont(
    val icon: Int,
    val value: Any
) {
    object FontSmall : ItemBottomFont(icon = R.drawable.ic_aa, value = 12.sp)
    object FontMedium : ItemBottomFont(icon = R.drawable.ic_aa, value = 16.sp)
    object FontLarge : ItemBottomFont(icon = R.drawable.ic_aa, value = 20.sp)

    object PositionMiddle :
        ItemBottomFont(icon = R.drawable.ic_position_middle, value = TextAlign.Center)

    object PositionLeft : ItemBottomFont(icon = R.drawable.ic_position_left, value = TextAlign.Left)

    object PositionRight :
        ItemBottomFont(icon = R.drawable.ic_postion_right, value = TextAlign.Left)
}

