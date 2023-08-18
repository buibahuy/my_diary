package com.example.mydiary.database

import androidx.room.Entity

@Entity(tableName = "Diary")
data class Diary(
    var title: String,
    var content: String,
    var mood: Mood = Mood.Default,
    var time: Long
) {

}
