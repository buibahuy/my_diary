package com.example.mydiary.diary

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mydiary.R
import com.example.mydiary.ui.theme.Primary

@Composable
fun ToolBarDiary(
    onClickBack: () -> Unit,
    onClickPreview: () -> Unit,
    onClickSave: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(vertical = 8.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .background(color = Color.White, shape = CircleShape)
                .size(30.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .clickable {
                        onClickBack()
                    }
                    .size(16.dp),
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = null,
                tint = Primary
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        Box(modifier = Modifier
            .clickable {
                onClickPreview()
            }
            .background(
                color = Color.White.copy(alpha = 0.5f),
                shape = RoundedCornerShape(4.dp)
            )
            .border(width = 2.dp, color = Color.White, shape = RoundedCornerShape(4.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Icon(
                modifier = Modifier
                    .size(16.dp),
                painter = painterResource(id = R.drawable.ic_preview),
                contentDescription = null,
                tint = Primary
            )
        }
        Spacer(modifier = Modifier.size(8.dp))
        Row(
            modifier = Modifier
                .clickable {
                    onClickSave()
                }
                .background(
                    color = Color.White.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(4.dp)
                )
                .border(width = 2.dp, color = Color.White, shape = RoundedCornerShape(4.dp))
                .padding(horizontal = 16.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Icon(
                modifier = Modifier
                    .size(16.dp),
                painter = painterResource(id = R.drawable.ic_save),
                contentDescription = null,
                tint = Primary
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(text = "Save", color = Primary)
        }

    }
}

@Preview
@Composable
fun PreviewToolBar() {
    ToolBarDiary(onClickBack = { /*TODO*/ }, onClickPreview = { /*TODO*/ }) {

    }
}