package com.example.mydiary.diary

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mydiary.R

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
        Icon(
            modifier = Modifier
                .clickable {
                    onClickBack()
                }.size(16.dp),
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = null
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            modifier = Modifier
                .clickable {
                    onClickPreview()
                }.size(16.dp),
            painter = painterResource(id = R.drawable.ic_preview),
            contentDescription = null
        )
        Row(
            modifier = Modifier
                .clickable {
                    onClickSave()
                }.padding(start = 16.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.padding(horizontal = 4.dp).size(16.dp),
                painter = painterResource(id = R.drawable.ic_save),
                contentDescription = null
            )
            Text(text = "Save")
        }

    }
}

@Preview
@Composable
fun PreviewToolBar() {
    ToolBarDiary(onClickBack = { /*TODO*/ }, onClickPreview = { /*TODO*/ }) {

    }
}