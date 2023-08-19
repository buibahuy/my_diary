package com.example.mydiary.database

import android.graphics.fonts.FontStyle
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Diary")
data class Diary(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    @ColumnInfo("title")
    var title: String? = null,
    @ColumnInfo("content")
    var content: String? = null,
    @ColumnInfo("mood")
    var mood: Int = Mood.Default.name,
    @ColumnInfo("time")
    var time: Long? = null,
    @ColumnInfo("background")
    var background: Int? = null,
//    @ColumnInfo("photo")
//    var photo: String? = null,
//    @ColumnInfo("fontSize")
//    var fontSize: TextUnit? = TextUnit.Unspecified,
//    @ColumnInfo("textAlign")
//    var textAlign: String? = TextAlign.Start.toString(),
//    @ColumnInfo("textColor")
//    var textColor: Color? = null,
    @ColumnInfo("tag")
    var tag: String? = null
)