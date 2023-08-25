package com.example.mydiary.database

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.mydiary.mood.Mood
import com.google.gson.Gson

@Entity(tableName = "Diary")
data class Diary(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    @ColumnInfo("title")
    var title: String? = null,
    @ColumnInfo("content")
    var content: String? = null,
    @ColumnInfo("content_second")
    var contentSecond: String? = null,
    @ColumnInfo("mood")
    var mood: Int = Mood.Default.icon,
    @ColumnInfo("time")
    var time: Long? = null,
    @ColumnInfo("background")
    var background: Int? = null,
    @ColumnInfo("photo")
    var photo: List<String> = emptyList(),
//    @ColumnInfo("fontSize")
//    var fontSize: TextUnit? = TextUnit.Unspecified,
//    @ColumnInfo("textAlign")
//    var textAlign: String? = TextAlign.Start.toString(),
//    @ColumnInfo("textColor")
//    var textColor: Color? = null,
    @ColumnInfo("tag")
    var listTag: List<String> = emptyList()
)

class Converters {

    @TypeConverter
    fun listToJson(value: List<String>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()
}