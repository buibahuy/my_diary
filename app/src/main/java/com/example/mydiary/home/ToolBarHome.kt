package com.example.mydiary.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mydiary.R

@Composable
fun ToolBarHome(
    onClickSearch: () -> Unit,
    onSortOldest: () -> Unit,
    onSortNewest: () -> Unit,
) {
    var showMenuSort by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(vertical = 8.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = "My Diary", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            modifier = Modifier
                .clickable {
                    onClickSearch()
                }
                .size(24.dp),
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = null,
            tint = Color.Black
        )
        Spacer(modifier = Modifier.size(16.dp))
        Box(modifier = Modifier.size(16.dp)){
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        showMenuSort = !showMenuSort
                    }
                    .size(16.dp),
                painter = painterResource(id = R.drawable.ic_sort),
                contentDescription = null,
                tint = Color.Black
            )
            DropdownMenu(
                expanded = showMenuSort,
                onDismissRequest = { showMenuSort = false }) {
                DropdownMenuItem(onClick = {
                    onSortOldest()
                }, text = {
                    Text(text = "Oldest")
                })
                DropdownMenuItem(onClick = {
                    onSortNewest()
                }, text = {
                    Text(text = "Newest")
                })
            }
        }
    }
}

@Preview
@Composable
fun PreviewToolBarHome() {
    ToolBarHome(onClickSearch = { /*TODO*/ }, onSortNewest = {}, onSortOldest = {})
}