package com.example.mydiary.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mydiary.R

@Composable
fun ToolBarHome(
    onClickSearch: () -> Unit,
    onClickSort: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(vertical = 8.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = "My Diary", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            modifier = Modifier
                .clickable {
                    onClickSearch()
                }
                .size(24.dp),
            painter = painterResource(id = R.drawable.ic_preview),
            contentDescription = null
        )
        Spacer(modifier = Modifier.size(16.dp))
        Icon(
            modifier = Modifier
                .size(24.dp)
                .clickable {
                    onClickSort()
                }
                .size(16.dp),
            painter = painterResource(id = R.drawable.ic_save),
            contentDescription = null
        )

    }
}

@Preview
@Composable
fun PreviewToolBarHome() {
    ToolBarHome(onClickSearch = { /*TODO*/ }) {
        
    }
}