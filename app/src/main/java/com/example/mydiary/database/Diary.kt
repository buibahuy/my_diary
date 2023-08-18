package com.example.mydiary.database

import android.graphics.fonts.FontStyle
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Diary")
data class Diary(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var title: String,
    var content: String,
    var mood: Mood = Mood.Default,
    var time: Long,
    var background: Int,
    var photo: List<String>,
    var fontSize: TextUnit,
    var textAlign: TextAlign,
    var textColor: Color,
    var tag: String
)